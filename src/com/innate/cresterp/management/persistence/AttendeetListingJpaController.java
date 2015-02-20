/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.management.persistence;

import com.innate.cresterp.management.entities.AttendeetListing;
import com.innate.cresterp.insurance.risk.persistence.Configuration;
import com.innate.cresterp.insurance.risk.exceptions.NonexistentEntityException;
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
public class AttendeetListingJpaController implements Serializable {

    public AttendeetListingJpaController(EntityManagerFactory emf) {
          this.emf = new Configuration().generateEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AttendeetListing attendeetListing) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(attendeetListing);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AttendeetListing attendeetListing) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            attendeetListing = em.merge(attendeetListing);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = attendeetListing.getId();
                if (findAttendeetListing(id) == null) {
                    throw new NonexistentEntityException("The attendeetListing with id " + id + " no longer exists.");
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
            AttendeetListing attendeetListing;
            try {
                attendeetListing = em.getReference(AttendeetListing.class, id);
                attendeetListing.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attendeetListing with id " + id + " no longer exists.", enfe);
            }
            em.remove(attendeetListing);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AttendeetListing> findAttendeetListingEntities() {
        return findAttendeetListingEntities(true, -1, -1);
    }

    public List<AttendeetListing> findAttendeetListingEntities(int maxResults, int firstResult) {
        return findAttendeetListingEntities(false, maxResults, firstResult);
    }

    private List<AttendeetListing> findAttendeetListingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AttendeetListing.class));
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

    public AttendeetListing findAttendeetListing(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AttendeetListing.class, id);
        } finally {
            em.close();
        }
    }

    public int getAttendeetListingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AttendeetListing> rt = cq.from(AttendeetListing.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
