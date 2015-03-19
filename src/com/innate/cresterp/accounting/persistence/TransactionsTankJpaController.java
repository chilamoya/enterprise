/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.accounting.persistence;

import com.innate.cresterp.accounting.entities.TransactionsTank;
import com.innate.cresterp.accounting.persistence.exceptions.NonexistentEntityException;
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
 * @author Tafadzwa
 */
public class TransactionsTankJpaController implements Serializable {

    public TransactionsTankJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransactionsTank transactionsTank) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(transactionsTank);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TransactionsTank transactionsTank) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            transactionsTank = em.merge(transactionsTank);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = transactionsTank.getId();
                if (findTransactionsTank(id) == null) {
                    throw new NonexistentEntityException("The transactionsTank with id " + id + " no longer exists.");
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
            TransactionsTank transactionsTank;
            try {
                transactionsTank = em.getReference(TransactionsTank.class, id);
                transactionsTank.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactionsTank with id " + id + " no longer exists.", enfe);
            }
            em.remove(transactionsTank);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TransactionsTank> findTransactionsTankEntities() {
        return findTransactionsTankEntities(true, -1, -1);
    }

    public List<TransactionsTank> findTransactionsTankEntities(int maxResults, int firstResult) {
        return findTransactionsTankEntities(false, maxResults, firstResult);
    }

    private List<TransactionsTank> findTransactionsTankEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransactionsTank.class));
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

    public TransactionsTank findTransactionsTank(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransactionsTank.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionsTankCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransactionsTank> rt = cq.from(TransactionsTank.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
