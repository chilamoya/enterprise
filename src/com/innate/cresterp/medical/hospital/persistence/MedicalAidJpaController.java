/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.medical.hospital.persistence;

import com.innate.cresterp.exceptions.NonexistentEntityException;
import com.innate.cresterp.medical.hospital.entities.MedicalAid;
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
public class MedicalAidJpaController implements Serializable {

    public MedicalAidJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("DebtorsDemoPersistancePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MedicalAid medicalAid) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(medicalAid);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MedicalAid medicalAid) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            medicalAid = em.merge(medicalAid);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = medicalAid.getId();
                if (findMedicalAid(id) == null) {
                    throw new NonexistentEntityException("The medicalAid with id " + id + " no longer exists.");
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
            MedicalAid medicalAid;
            try {
                medicalAid = em.getReference(MedicalAid.class, id);
                medicalAid.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medicalAid with id " + id + " no longer exists.", enfe);
            }
            em.remove(medicalAid);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MedicalAid> findMedicalAidEntities() {
        return findMedicalAidEntities(true, -1, -1);
    }

    public List<MedicalAid> findMedicalAidEntities(int maxResults, int firstResult) {
        return findMedicalAidEntities(false, maxResults, firstResult);
    }

    private List<MedicalAid> findMedicalAidEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MedicalAid.class));
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

    public MedicalAid findMedicalAid(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MedicalAid.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicalAidCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MedicalAid> rt = cq.from(MedicalAid.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
