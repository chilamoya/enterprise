/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.pos.persistence;

import com.innate.cresterp.insurance.risk.persistence.Configuration;
import com.innate.cresterp.pos.entities.POSSetting;
import com.innate.cresterp.pos.persistence.exceptions.NonexistentEntityException;
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
public class POSSettingJpaController implements Serializable {

    public POSSettingJpaController() {
        this.emf = new Configuration().generateEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(POSSetting POSSetting) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(POSSetting);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(POSSetting POSSetting) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            POSSetting = em.merge(POSSetting);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = POSSetting.getId();
                if (findPOSSetting(id) == null) {
                    throw new NonexistentEntityException("The pOSSetting with id " + id + " no longer exists.");
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
            POSSetting POSSetting;
            try {
                POSSetting = em.getReference(POSSetting.class, id);
                POSSetting.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The POSSetting with id " + id + " no longer exists.", enfe);
            }
            em.remove(POSSetting);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<POSSetting> findPOSSettingEntities() {
        return findPOSSettingEntities(true, -1, -1);
    }

    public List<POSSetting> findPOSSettingEntities(int maxResults, int firstResult) {
        return findPOSSettingEntities(false, maxResults, firstResult);
    }

    private List<POSSetting> findPOSSettingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(POSSetting.class));
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

    public POSSetting findPOSSetting(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(POSSetting.class, id);
        } finally {
            em.close();
        }
    }

    public int getPOSSettingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<POSSetting> rt = cq.from(POSSetting.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
