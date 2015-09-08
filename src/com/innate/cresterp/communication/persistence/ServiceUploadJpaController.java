/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.communication.persistence;

import com.innate.cresterp.communication.entities.ServiceUpload;
import com.innate.cresterp.communication.persistence.exceptions.NonexistentEntityException;
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
 * @author Tafadzwa
 */
public class ServiceUploadJpaController implements Serializable {

    public ServiceUploadJpaController(EntityManagerFactory emf) {
        this.emf = new Configuration().generateEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ServiceUpload serviceUpload) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(serviceUpload);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ServiceUpload serviceUpload) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            serviceUpload = em.merge(serviceUpload);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = serviceUpload.getId();
                if (findServiceUpload(id) == null) {
                    throw new NonexistentEntityException("The serviceUpload with id " + id + " no longer exists.");
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
            ServiceUpload serviceUpload;
            try {
                serviceUpload = em.getReference(ServiceUpload.class, id);
                serviceUpload.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The serviceUpload with id " + id + " no longer exists.", enfe);
            }
            em.remove(serviceUpload);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ServiceUpload> findServiceUploadEntities() {
        return findServiceUploadEntities(true, -1, -1);
    }

    public List<ServiceUpload> findServiceUploadEntities(int maxResults, int firstResult) {
        return findServiceUploadEntities(false, maxResults, firstResult);
    }

    private List<ServiceUpload> findServiceUploadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ServiceUpload.class));
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

    public ServiceUpload findServiceUpload(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ServiceUpload.class, id);
        } finally {
            em.close();
        }
    }

    public int getServiceUploadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ServiceUpload> rt = cq.from(ServiceUpload.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
