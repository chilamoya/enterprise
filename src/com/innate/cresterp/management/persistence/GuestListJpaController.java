/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.management.persistence;

import com.innate.cresterp.management.entities.GuestList;
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
public class GuestListJpaController implements Serializable {

    public GuestListJpaController(EntityManagerFactory emf) {
             this.emf = new Configuration().generateEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GuestList guestList) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(guestList);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GuestList guestList) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            guestList = em.merge(guestList);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = guestList.getId();
                if (findGuestList(id) == null) {
                    throw new NonexistentEntityException("The guestList with id " + id + " no longer exists.");
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
            GuestList guestList;
            try {
                guestList = em.getReference(GuestList.class, id);
                guestList.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The guestList with id " + id + " no longer exists.", enfe);
            }
            em.remove(guestList);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GuestList> findGuestListEntities() {
        return findGuestListEntities(true, -1, -1);
    }

    public List<GuestList> findGuestListEntities(int maxResults, int firstResult) {
        return findGuestListEntities(false, maxResults, firstResult);
    }

    private List<GuestList> findGuestListEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GuestList.class));
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

    public GuestList findGuestList(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GuestList.class, id);
        } finally {
            em.close();
        }
    }

    public int getGuestListCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GuestList> rt = cq.from(GuestList.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
