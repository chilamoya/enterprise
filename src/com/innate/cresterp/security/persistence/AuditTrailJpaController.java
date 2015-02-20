/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.security.persistence;

import com.innate.cresterp.security.entities.AuditTrail;
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
public class AuditTrailJpaController implements Serializable {

    public AuditTrailJpaController(EntityManagerFactory emf) {
            this.emf = new Configuration().generateEntityManagerFactory();

    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AuditTrail auditTrail) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(auditTrail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AuditTrail auditTrail) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            auditTrail = em.merge(auditTrail);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = auditTrail.getId();
                if (findAuditTrail(id) == null) {
                    throw new NonexistentEntityException("The auditTrail with id " + id + " no longer exists.");
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
            AuditTrail auditTrail;
            try {
                auditTrail = em.getReference(AuditTrail.class, id);
                auditTrail.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The auditTrail with id " + id + " no longer exists.", enfe);
            }
            em.remove(auditTrail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AuditTrail> findAuditTrailEntities() {
        return findAuditTrailEntities(true, -1, -1);
    }

    public List<AuditTrail> findAuditTrailEntities(int maxResults, int firstResult) {
        return findAuditTrailEntities(false, maxResults, firstResult);
    }

    private List<AuditTrail> findAuditTrailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AuditTrail.class));
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

    public AuditTrail findAuditTrail(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AuditTrail.class, id);
        } finally {
            em.close();
        }
    }

    public int getAuditTrailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AuditTrail> rt = cq.from(AuditTrail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
