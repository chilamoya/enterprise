/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.medical.hospital.persistence;

import com.innate.cresterp.exceptions.NonexistentEntityException;
import com.innate.cresterp.insurance.risk.persistence.Configuration;
import com.innate.cresterp.medical.hospital.entities.PatientRecord;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Edmund
 */
public class PatientRecordJpaController {

    public PatientRecordJpaController() {
     this.emf = new Configuration().generateEntityManagerFactory();
 }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<PatientRecord> getpatient(String pp){
        List<PatientRecord> listt = new ArrayList<PatientRecord>();
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("findPatient");
        q.setParameter("hp", pp);
        listt = q.getResultList();
        return listt;
    }
     public List<PatientRecord> getAllPatients(){
        List<PatientRecord> listt = new ArrayList<PatientRecord>();
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("getAllPatients");
//        q.setParameter("hp", pp);
        listt = q.getResultList();
        return listt;
    }


    public void create(PatientRecord patientRecord) {
        EntityManager em = null;
        Timestamp ts = new Timestamp(new Date().getTime());
        long count = getPatientRecordCount() + 1;
        patientRecord.setHospitalNumber("CL-"+count);
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(patientRecord);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PatientRecord patientRecord) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            patientRecord = em.merge(patientRecord);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = patientRecord.getId();
                if (findPatientRecord(id) == null) {
                    throw new NonexistentEntityException("The patientRecord with id " + id + " no longer exists.");
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
            PatientRecord patientRecord;
            try {
                patientRecord = em.getReference(PatientRecord.class, id);
                patientRecord.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The patientRecord with id " + id + " no longer exists.", enfe);
            }
            em.remove(patientRecord);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PatientRecord> findPatientRecordEntities() {
        return findPatientRecordEntities(true, -1, -1);
    }

    public List<PatientRecord> findPatientRecordEntities(int maxResults, int firstResult) {
        return findPatientRecordEntities(false, maxResults, firstResult);
    }

    private List<PatientRecord> findPatientRecordEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PatientRecord.class));
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

    public PatientRecord findPatientRecord(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PatientRecord.class, id);
        } finally {
            em.close();
        }
    }

    public int getPatientRecordCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PatientRecord> rt = cq.from(PatientRecord.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
