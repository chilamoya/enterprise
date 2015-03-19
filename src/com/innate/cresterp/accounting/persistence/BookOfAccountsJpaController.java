/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.accounting.persistence;

import com.innate.cresterp.accounting.entities.BookOfAccounts;
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
public class BookOfAccountsJpaController implements Serializable {

    public BookOfAccountsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BookOfAccounts bookOfAccounts) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(bookOfAccounts);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BookOfAccounts bookOfAccounts) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            bookOfAccounts = em.merge(bookOfAccounts);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = bookOfAccounts.getId();
                if (findBookOfAccounts(id) == null) {
                    throw new NonexistentEntityException("The bookOfAccounts with id " + id + " no longer exists.");
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
            BookOfAccounts bookOfAccounts;
            try {
                bookOfAccounts = em.getReference(BookOfAccounts.class, id);
                bookOfAccounts.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bookOfAccounts with id " + id + " no longer exists.", enfe);
            }
            em.remove(bookOfAccounts);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BookOfAccounts> findBookOfAccountsEntities() {
        return findBookOfAccountsEntities(true, -1, -1);
    }

    public List<BookOfAccounts> findBookOfAccountsEntities(int maxResults, int firstResult) {
        return findBookOfAccountsEntities(false, maxResults, firstResult);
    }

    private List<BookOfAccounts> findBookOfAccountsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BookOfAccounts.class));
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

    public BookOfAccounts findBookOfAccounts(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BookOfAccounts.class, id);
        } finally {
            em.close();
        }
    }

    public int getBookOfAccountsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BookOfAccounts> rt = cq.from(BookOfAccounts.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
