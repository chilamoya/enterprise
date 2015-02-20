/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.medical.hospital.persistence;

import com.innate.cresterp.exceptions.NonexistentEntityException;
import com.innate.cresterp.medical.hospital.entities.BiillingPoint;
import com.innate.cresterp.medical.hospital.entities.CashSubmission;
import java.util.ArrayList;
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
public class CashSubmissionJpaController {

    public CashSubmissionJpaController() {
        emf = Persistence.createEntityManagerFactory("DebtorsDemoPersistancePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<CashSubmission> submitted(BiillingPoint b, String d){
      EntityManager e = emf.createEntityManager();
      List<CashSubmission> pList = new ArrayList<CashSubmission>();
      Query q = e.createNamedQuery("submitted");
      q.setParameter("d", d);
      q.setParameter("bpid", b);
      pList = q.getResultList();
      return pList;
  }

    public void create(CashSubmission cashSubmission) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cashSubmission);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CashSubmission cashSubmission) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cashSubmission = em.merge(cashSubmission);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = cashSubmission.getId();
                if (findCashSubmission(id) == null) {
                    throw new NonexistentEntityException("The cashSubmission with id " + id + " no longer exists.");
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
            CashSubmission cashSubmission;
            try {
                cashSubmission = em.getReference(CashSubmission.class, id);
                cashSubmission.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cashSubmission with id " + id + " no longer exists.", enfe);
            }
            em.remove(cashSubmission);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CashSubmission> findCashSubmissionEntities() {
        return findCashSubmissionEntities(true, -1, -1);
    }

    public List<CashSubmission> findCashSubmissionEntities(int maxResults, int firstResult) {
        return findCashSubmissionEntities(false, maxResults, firstResult);
    }

    private List<CashSubmission> findCashSubmissionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CashSubmission.class));
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

    public CashSubmission findCashSubmission(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CashSubmission.class, id);
        } finally {
            em.close();
        }
    }

    public int getCashSubmissionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CashSubmission> rt = cq.from(CashSubmission.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
