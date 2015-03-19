/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.accounting.persistence;

import com.innate.cresterp.accounting.entities.AccountProcessTransaction;
import com.innate.cresterp.accounting.persistence.exceptions.NonexistentEntityException;
import com.innate.cresterp.insurance.risk.persistence.Configuration;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author user
 */
public class AccountProcessTransactionJpaController implements Serializable {

    public AccountProcessTransactionJpaController(EntityManagerFactory emf) {
        this.emf = new Configuration().generateEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AccountProcessTransaction accountProcessTransaction) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(accountProcessTransaction);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AccountProcessTransaction accountProcessTransaction) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            accountProcessTransaction = em.merge(accountProcessTransaction);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = accountProcessTransaction.getId();
                if (findAccountProcessTransaction(id) == null) {
                    throw new NonexistentEntityException("The accountProcessTransaction with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AccountProcessTransaction accountProcessTransaction;
            try {
                accountProcessTransaction = em.getReference(AccountProcessTransaction.class, id);
                accountProcessTransaction.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accountProcessTransaction with id " + id + " no longer exists.", enfe);
            }
            em.remove(accountProcessTransaction);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AccountProcessTransaction> findAccountProcessTransactionEntities() {
        return findAccountProcessTransactionEntities(true, -1, -1);
    }

    public List<AccountProcessTransaction> findAccountProcessTransactionEntities(int maxResults, int firstResult) {
        return findAccountProcessTransactionEntities(false, maxResults, firstResult);
    }

    private List<AccountProcessTransaction> findAccountProcessTransactionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AccountProcessTransaction.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AccountProcessTransaction findAccountProcessTransaction(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AccountProcessTransaction.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccountProcessTransactionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AccountProcessTransaction> rt = cq.from(AccountProcessTransaction.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
