/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.medical.hospital.persistence;

import com.innate.cresterp.exceptions.NonexistentEntityException;
import com.innate.cresterp.medical.hospital.entities.BiillingPoint;
import com.innate.cresterp.medical.hospital.entities.Uusers;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.innate.cresterp.medical.hospital.entities.Deepartment;
import com.innate.cresterp.medical.hospital.entities.PatientRecord;
import java.util.ArrayList;

/**
 *
 * @author Edmund
 */
public class UusersJpaController {

    public UusersJpaController() {
        emf = Persistence.createEntityManagerFactory("DebtorsDemoPersistancePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

        public List<Uusers> getUsersOnBillingPoint( BiillingPoint bp ){
        List<Uusers> list = new ArrayList<Uusers>();
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("getUsersOnBillingPoint");
        q.setParameter("bp", bp);
        list = q.getResultList();
       return list;
    }

    public void create(Uusers uusers) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Deepartment departmentId = uusers.getDepartmentId();
            if (departmentId != null) {
                departmentId = em.getReference(departmentId.getClass(), departmentId.getId());
                uusers.setDepartmentId(departmentId);
            }
            em.persist(uusers);
            if (departmentId != null) {
                departmentId.getUsersList().add(uusers);
                departmentId = em.merge(departmentId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Uusers uusers) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Uusers persistentUusers = em.find(Uusers.class, uusers.getId());
            Deepartment departmentIdOld = persistentUusers.getDepartmentId();
            Deepartment departmentIdNew = uusers.getDepartmentId();
            if (departmentIdNew != null) {
                departmentIdNew = em.getReference(departmentIdNew.getClass(), departmentIdNew.getId());
                uusers.setDepartmentId(departmentIdNew);
            }
            uusers = em.merge(uusers);
            if (departmentIdOld != null && !departmentIdOld.equals(departmentIdNew)) {
                departmentIdOld.getUsersList().remove(uusers);
                departmentIdOld = em.merge(departmentIdOld);
            }
            if (departmentIdNew != null && !departmentIdNew.equals(departmentIdOld)) {
                departmentIdNew.getUsersList().add(uusers);
                departmentIdNew = em.merge(departmentIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = uusers.getId();
                if (findUusers(id) == null) {
                    throw new NonexistentEntityException("The uusers with id " + id + " no longer exists.");
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
            Uusers uusers;
            try {
                uusers = em.getReference(Uusers.class, id);
                uusers.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The uusers with id " + id + " no longer exists.", enfe);
            }
            Deepartment departmentId = uusers.getDepartmentId();
            if (departmentId != null) {
                departmentId.getUsersList().remove(uusers);
                departmentId = em.merge(departmentId);
            }
            em.remove(uusers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Uusers> findUusersEntities() {
        return findUusersEntities(true, -1, -1);
    }

    public List<Uusers> findUusersEntities(int maxResults, int firstResult) {
        return findUusersEntities(false, maxResults, firstResult);
    }

    private List<Uusers> findUusersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Uusers.class));
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

    public Uusers findUusers(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Uusers.class, id);
        } finally {
            em.close();
        }
    }

    public int getUusersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Uusers> rt = cq.from(Uusers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
