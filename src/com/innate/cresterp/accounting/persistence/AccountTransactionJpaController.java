/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.accounting.persistence;

import com.innate.cresterp.accounting.entities.AccountTransactionDefinition;
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
public class AccountTransactionJpaController implements Serializable {

    public AccountTransactionJpaController(EntityManagerFactory emf) {
        this.emf = new Configuration().generateEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AccountTransactionDefinition accountTransaction) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(accountTransaction);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AccountTransactionDefinition accountTransaction) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            accountTransaction = em.merge(accountTransaction);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = accountTransaction.getId();
                if (findAccountTransaction(id) == null) {
                    throw new NonexistentEntityException("The accountTransaction with id " + id + " no longer exists.");
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
            AccountTransactionDefinition accountTransaction;
            try {
                accountTransaction = em.getReference(AccountTransactionDefinition.class, id);
                accountTransaction.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accountTransaction with id " + id + " no longer exists.", enfe);
            }
            em.remove(accountTransaction);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AccountTransactionDefinition> findAccountTransactionEntities() {
        return findAccountTransactionEntities(true, -1, -1);
    }

    public List<AccountTransactionDefinition> findAccountTransactionEntities(int maxResults, int firstResult) {
        return findAccountTransactionEntities(false, maxResults, firstResult);
    }

    private List<AccountTransactionDefinition> findAccountTransactionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AccountTransactionDefinition.class));
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

    public AccountTransactionDefinition findAccountTransaction(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AccountTransactionDefinition.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccountTransactionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AccountTransactionDefinition> rt = cq.from(AccountTransactionDefinition.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
