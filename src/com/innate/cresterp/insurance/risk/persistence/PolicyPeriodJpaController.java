/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.insurance.risk.persistence;

import com.innate.cresterp.insurance.risk.exceptions.NonexistentEntityException;
import com.innate.cresterp.insurance.risk.entities.PolicyPeriod;
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
public class PolicyPeriodJpaController implements Serializable {

    public PolicyPeriodJpaController() {
      
        this.emf = new Configuration().generateEntityManagerFactory();
    }
     private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PolicyPeriod policyPeriod) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(policyPeriod);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PolicyPeriod policyPeriod) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            policyPeriod = em.merge(policyPeriod);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = policyPeriod.getId();
                if (findPolicyPeriod(id) == null) {
                    throw new NonexistentEntityException("The policyPeriod with id " + id + " no longer exists.");
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
            PolicyPeriod policyPeriod;
            try {
                policyPeriod = em.getReference(PolicyPeriod.class, id);
                policyPeriod.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The policyPeriod with id " + id + " no longer exists.", enfe);
            }
            em.remove(policyPeriod);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PolicyPeriod> findPolicyPeriodEntities() {
        return findPolicyPeriodEntities(true, -1, -1);
    }

    public List<PolicyPeriod> findPolicyPeriodEntities(int maxResults, int firstResult) {
        return findPolicyPeriodEntities(false, maxResults, firstResult);
    }

    private List<PolicyPeriod> findPolicyPeriodEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PolicyPeriod.class));
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

    public PolicyPeriod findPolicyPeriod(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PolicyPeriod.class, id);
        } finally {
            em.close();
        }
    }

    public int getPolicyPeriodCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PolicyPeriod> rt = cq.from(PolicyPeriod.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
