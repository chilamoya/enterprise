/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.insurance.risk.persistence;

import com.innate.cresterp.insurance.risk.exceptions.NonexistentEntityException;
import com.innate.cresterp.insurance.risk.entities.PolicyCategory;
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
public class PolicyCategoryJpaController implements Serializable {

    public PolicyCategoryJpaController() {
  this.emf = new Configuration().generateEntityManagerFactory();

    }
     private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PolicyCategory policyCategory) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(policyCategory);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PolicyCategory policyCategory) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            policyCategory = em.merge(policyCategory);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = policyCategory.getId();
                if (findPolicyCategory(id) == null) {
                    throw new NonexistentEntityException("The policyCategory with id " + id + " no longer exists.");
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
            PolicyCategory policyCategory;
            try {
                policyCategory = em.getReference(PolicyCategory.class, id);
                policyCategory.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The policyCategory with id " + id + " no longer exists.", enfe);
            }
            em.remove(policyCategory);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PolicyCategory> findPolicyCategoryEntities() {
        return findPolicyCategoryEntities(true, -1, -1);
    }

    public List<PolicyCategory> findPolicyCategoryEntities(int maxResults, int firstResult) {
        return findPolicyCategoryEntities(false, maxResults, firstResult);
    }

    private List<PolicyCategory> findPolicyCategoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PolicyCategory.class));
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

    public PolicyCategory findPolicyCategory(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PolicyCategory.class, id);
        } finally {
            em.close();
        }
    }

    public int getPolicyCategoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PolicyCategory> rt = cq.from(PolicyCategory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
