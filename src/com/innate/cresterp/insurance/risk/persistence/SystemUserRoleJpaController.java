/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.insurance.risk.persistence;

import com.innate.cresterp.insurance.risk.exceptions.NonexistentEntityException;
import com.innate.cresterp.insurance.risk.entities.SystemUserRole;
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
public class SystemUserRoleJpaController implements Serializable {

    public SystemUserRoleJpaController(EntityManagerFactory emf) {
       this.emf =new Configuration().generateEntityManagerFactory();//
       //Persistence.createEntityManagerFactory("DebtorsDemoPersistancePU");
 
    }
   
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SystemUserRole systemUserRole) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(systemUserRole);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SystemUserRole systemUserRole) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            systemUserRole = em.merge(systemUserRole);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = systemUserRole.getId();
                if (findSystemUserRole(id) == null) {
                    throw new NonexistentEntityException("The systemUserRole with id " + id + " no longer exists.");
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
            SystemUserRole systemUserRole;
            try {
                systemUserRole = em.getReference(SystemUserRole.class, id);
                systemUserRole.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The systemUserRole with id " + id + " no longer exists.", enfe);
            }
            em.remove(systemUserRole);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SystemUserRole> findSystemUserRoleEntities() {
        return findSystemUserRoleEntities(true, -1, -1);
    }

    public List<SystemUserRole> findSystemUserRoleEntities(int maxResults, int firstResult) {
        return findSystemUserRoleEntities(false, maxResults, firstResult);
    }

    private List<SystemUserRole> findSystemUserRoleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SystemUserRole.class));
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

    public SystemUserRole findSystemUserRole(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SystemUserRole.class, id);
        } finally {
            em.close();
        }
    }

    public int getSystemUserRoleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SystemUserRole> rt = cq.from(SystemUserRole.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
