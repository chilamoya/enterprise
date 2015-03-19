/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.insurance.risk.persistence;

import com.innate.cresterp.insurance.risk.exceptions.NonexistentEntityException;
import com.innate.cresterp.insurance.risk.entities.SystemUser;
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
public class SystemUserJpaController implements Serializable {

    public SystemUserJpaController( ) {
       this.emf = new Configuration().generateEntityManagerFactory();
 
    }
     private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        
     return   emf.createEntityManager();
        
          
    }

    public void create(SystemUser systemUser) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(systemUser);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SystemUser systemUser) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            systemUser = em.merge(systemUser);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = systemUser.getId();
                if (findSystemUser(id) == null) {
                    throw new NonexistentEntityException("The systemUser with id " + id + " no longer exists.");
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
            SystemUser systemUser;
            try {
                systemUser = em.getReference(SystemUser.class, id);
                systemUser.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The systemUser with id " + id + " no longer exists.", enfe);
            }
            em.remove(systemUser);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SystemUser> findSystemUserEntities() {
        return findSystemUserEntities(true, -1, -1);
    }

    public List<SystemUser> findSystemUserEntities(int maxResults, int firstResult) {
        return findSystemUserEntities(false, maxResults, firstResult);
    }

    private List<SystemUser> findSystemUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SystemUser.class));
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

    public SystemUser findSystemUser(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SystemUser.class, id);
        } finally {
            em.close();
        }
    }

    public int getSystemUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SystemUser> rt = cq.from(SystemUser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
