/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.insurance.risk.persistence;

import com.innate.cresterp.insurance.risk.exceptions.NonexistentEntityException;
import com.innate.cresterp.insurance.risk.entities.Renewal;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
 
/**
 *
 * @author Tafadzwa
 */
public class RenewalJpaController implements Serializable {

    public RenewalJpaController() {
           this.emf = new Configuration().generateEntityManagerFactory();
    }
     private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Renewal renewal) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(renewal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Renewal renewal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            renewal = em.merge(renewal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = renewal.getId();
                if (findRenewal(id) == null) {
                    throw new NonexistentEntityException("The renewal with id " + id + " no longer exists.");
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
            Renewal renewal;
            try {
                renewal = em.getReference(Renewal.class, id);
                renewal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The renewal with id " + id + " no longer exists.", enfe);
            }
            em.remove(renewal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Renewal> findRenewalEntities() {
        return findRenewalEntities(true, -1, -1);
    }

    public List<Renewal> findRenewalEntities(int maxResults, int firstResult) {
        return findRenewalEntities(false, maxResults, firstResult);
    }

    private List<Renewal> findRenewalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Renewal.class));
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

    public Renewal findRenewal(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Renewal.class, id);
        } finally {
            em.close();
        }
    }

    public int getRenewalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Renewal> rt = cq.from(Renewal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
