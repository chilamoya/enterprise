/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.insurance.risk.persistence;

import com.innate.cresterp.insurance.risk.exceptions.NonexistentEntityException;
import com.innate.cresterp.insurance.risk.entities.RiskPremium;
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
public class RiskPremiumJpaController implements Serializable {

    public RiskPremiumJpaController() {
         
        this.emf = new Configuration().generateEntityManagerFactory();
    }
     private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RiskPremium riskPremium) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(riskPremium);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RiskPremium riskPremium) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            riskPremium = em.merge(riskPremium);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = riskPremium.getId();
                if (findRiskPremium(id) == null) {
                    throw new NonexistentEntityException("The riskPremium with id " + id + " no longer exists.");
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
            RiskPremium riskPremium;
            try {
                riskPremium = em.getReference(RiskPremium.class, id);
                riskPremium.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The riskPremium with id " + id + " no longer exists.", enfe);
            }
            em.remove(riskPremium);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RiskPremium> findRiskPremiumEntities() {
        return findRiskPremiumEntities(true, -1, -1);
    }

    public List<RiskPremium> findRiskPremiumEntities(int maxResults, int firstResult) {
        return findRiskPremiumEntities(false, maxResults, firstResult);
    }

    private List<RiskPremium> findRiskPremiumEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RiskPremium.class));
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

    public RiskPremium findRiskPremium(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RiskPremium.class, id);
        } finally {
            em.close();
        }
    }

    public int getRiskPremiumCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RiskPremium> rt = cq.from(RiskPremium.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
