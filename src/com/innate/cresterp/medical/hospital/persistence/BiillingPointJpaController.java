/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.medical.hospital.persistence;

import com.innate.cresterp.exceptions.NonexistentEntityException;
import com.innate.cresterp.medical.hospital.entities.BiillingPoint;
import com.innate.cresterp.medical.hospital.entities.Uusers;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Edmund
 */
public class BiillingPointJpaController {

    public BiillingPointJpaController() {
        emf = Persistence.createEntityManagerFactory("DebtorsDemoPersistancePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
public List<BiillingPoint> getBillingPoints(){
    EntityManager em = emf.createEntityManager();
    Query q = em.createNamedQuery("billingPoints");
//    q.setParameter("uid", u);
    List<BiillingPoint> bList = q.getResultList();
    return bList;
}
    public void create(BiillingPoint biillingPoint) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(biillingPoint);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BiillingPoint biillingPoint) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            biillingPoint = em.merge(biillingPoint);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = biillingPoint.getId();
                if (findBiillingPoint(id) == null) {
                    throw new NonexistentEntityException("The biillingPoint with id " + id + " no longer exists.");
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
            BiillingPoint biillingPoint;
            try {
                biillingPoint = em.getReference(BiillingPoint.class, id);
                biillingPoint.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The biillingPoint with id " + id + " no longer exists.", enfe);
            }
            em.remove(biillingPoint);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BiillingPoint> findBiillingPointEntities() {
        return findBiillingPointEntities(true, -1, -1);
    }

    public List<BiillingPoint> findBiillingPointEntities(int maxResults, int firstResult) {
        return findBiillingPointEntities(false, maxResults, firstResult);
    }

    private List<BiillingPoint> findBiillingPointEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BiillingPoint.class));
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

    public BiillingPoint findBiillingPoint(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BiillingPoint.class, id);
        } finally {
            em.close();
        }
    }

    public int getBiillingPointCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BiillingPoint> rt = cq.from(BiillingPoint.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
