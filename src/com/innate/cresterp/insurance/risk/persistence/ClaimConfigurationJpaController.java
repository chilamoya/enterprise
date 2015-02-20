/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.insurance.risk.persistence;

import com.innate.cresterp.insurance.risk.exceptions.NonexistentEntityException;
import com.innate.cresterp.insurance.risk.entities.ClaimConfiguration;
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
public class ClaimConfigurationJpaController implements Serializable {

    public ClaimConfigurationJpaController() {
           this.emf = new Configuration().generateEntityManagerFactory();

    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClaimConfiguration claimConfiguration) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(claimConfiguration);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClaimConfiguration claimConfiguration) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            claimConfiguration = em.merge(claimConfiguration);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = claimConfiguration.getId();
                if (findClaimConfiguration(id) == null) {
                    throw new NonexistentEntityException("The claimConfiguration with id " + id + " no longer exists.");
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
            ClaimConfiguration claimConfiguration;
            try {
                claimConfiguration = em.getReference(ClaimConfiguration.class, id);
                claimConfiguration.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The claimConfiguration with id " + id + " no longer exists.", enfe);
            }
            em.remove(claimConfiguration);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClaimConfiguration> findClaimConfigurationEntities() {
        return findClaimConfigurationEntities(true, -1, -1);
    }

    public List<ClaimConfiguration> findClaimConfigurationEntities(int maxResults, int firstResult) {
        return findClaimConfigurationEntities(false, maxResults, firstResult);
    }

    private List<ClaimConfiguration> findClaimConfigurationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClaimConfiguration.class));
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

    public ClaimConfiguration findClaimConfiguration(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClaimConfiguration.class, id);
        } finally {
            em.close();
        }
    }

    public int getClaimConfigurationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClaimConfiguration> rt = cq.from(ClaimConfiguration.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
