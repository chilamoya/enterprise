/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.medical.hospital.persistence;

import com.innate.cresterp.exceptions.NonexistentEntityException;
import com.innate.cresterp.medical.hospital.entities.BiillingPoint;
import com.innate.cresterp.medical.hospital.entities.PaaymentsAdmission;
import com.innate.cresterp.medical.hospital.entities.PatientRecord;
import java.math.BigDecimal;
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
public class PaaymentsAdmissionJpaController {

    public PaaymentsAdmissionJpaController() {
        emf = Persistence.createEntityManagerFactory("DebtorsDemoPersistancePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<PaaymentsAdmission> getAdmissionPaymentByDate(Date startDate, Date endDate){

        EntityManager em = emf.createEntityManager();
        List<PaaymentsAdmission> list = new ArrayList<PaaymentsAdmission>();
     
        Query q = em.createQuery("Select p from PaaymentsAdmission p Where p.processedDate Between ?1 and ?2 ");
        q.setParameter(1, startDate);
        q.setParameter(2, endDate);

        list = q.getResultList();
        return list;


    }


  public List<PaaymentsAdmission> getAmountPaid(PatientRecord p){
      EntityManager e = emf.createEntityManager();
      List<PaaymentsAdmission> pList = new ArrayList<PaaymentsAdmission>();
      Query q = e.createNamedQuery("getAmountPaid");
      q.setParameter("pid", p);
      pList = q.getResultList();
      return pList;

  }

  public List<PaaymentsAdmission> totalReceipted(BiillingPoint bp, Date dp){
      EntityManager e = emf.createEntityManager();
      List<PaaymentsAdmission> pList = new ArrayList<PaaymentsAdmission>();
      Query q = e.createNamedQuery("totalReceipted");
      q.setParameter("bpid", bp);
      q.setParameter("dp", dp);
      pList = q.getResultList();
      return pList;

  }

    public void create(PaaymentsAdmission paaymentsAdmission) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(paaymentsAdmission);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PaaymentsAdmission paaymentsAdmission) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            paaymentsAdmission = em.merge(paaymentsAdmission);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = paaymentsAdmission.getId();
                if (findPaaymentsAdmission(id) == null) {
                    throw new NonexistentEntityException("The paaymentsAdmission with id " + id + " no longer exists.");
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
            PaaymentsAdmission paaymentsAdmission;
            try {
                paaymentsAdmission = em.getReference(PaaymentsAdmission.class, id);
                paaymentsAdmission.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paaymentsAdmission with id " + id + " no longer exists.", enfe);
            }
            em.remove(paaymentsAdmission);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PaaymentsAdmission> findPaaymentsAdmissionEntities() {
        return findPaaymentsAdmissionEntities(true, -1, -1);
    }

    public List<PaaymentsAdmission> findPaaymentsAdmissionEntities(int maxResults, int firstResult) {
        return findPaaymentsAdmissionEntities(false, maxResults, firstResult);
    }

    private List<PaaymentsAdmission> findPaaymentsAdmissionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PaaymentsAdmission.class));
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

    public PaaymentsAdmission findPaaymentsAdmission(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PaaymentsAdmission.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaaymentsAdmissionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PaaymentsAdmission> rt = cq.from(PaaymentsAdmission.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
