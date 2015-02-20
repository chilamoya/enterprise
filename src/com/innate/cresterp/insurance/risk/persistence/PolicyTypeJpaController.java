/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.insurance.risk.persistence;

import com.innate.cresterp.insurance.risk.exceptions.NonexistentEntityException;
import com.innate.cresterp.insurance.risk.entities.PolicyType;
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
public class PolicyTypeJpaController implements Serializable {

    public PolicyTypeJpaController() {
         
        this.emf = new Configuration().generateEntityManagerFactory();
    }
     private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PolicyType policyType) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(policyType);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PolicyType policyType) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            policyType = em.merge(policyType);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = policyType.getId();
                if (findPolicyType(id) == null) {
                    throw new NonexistentEntityException("The policyType with id " + id + " no longer exists.");
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
            PolicyType policyType;
            try {
                policyType = em.getReference(PolicyType.class, id);
                policyType.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The policyType with id " + id + " no longer exists.", enfe);
            }
            em.remove(policyType);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PolicyType> findPolicyTypeEntities() {
        return findPolicyTypeEntities(true, -1, -1);
    }

    public List<PolicyType> findPolicyTypeEntities(int maxResults, int firstResult) {
        return findPolicyTypeEntities(false, maxResults, firstResult);
    }

    private List<PolicyType> findPolicyTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PolicyType.class));
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

    public PolicyType findPolicyType(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PolicyType.class, id);
        } finally {
            em.close();
        }
    }

    public int getPolicyTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PolicyType> rt = cq.from(PolicyType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
