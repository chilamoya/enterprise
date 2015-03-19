/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.medical.hospital.persistence;

import com.innate.cresterp.exceptions.NonexistentEntityException;
import com.innate.cresterp.medical.hospital.entities.Biiills;
import com.innate.cresterp.medical.hospital.entities.BiillingPoint;
import com.innate.cresterp.medical.hospital.entities.Invoices;
import com.innate.cresterp.medical.hospital.entities.PatientRecord;
import com.innate.cresterp.medical.hospital.entities.Uusers;
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
public class InvoicesJpaController {

    public InvoicesJpaController() {
        emf = Persistence.createEntityManagerFactory("DebtorsDemoPersistancePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    

    public List<Invoices> getInvoiceNumber(PatientRecord pr) {

        List<Invoices> list = new ArrayList<Invoices>();
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("getInvoiceNumber");
        q.setParameter("pr", pr);
        list = q.getResultList();
        return list;
    }

    public List<Invoices> currentInvoice(Long id) {

        List<Invoices> list = new ArrayList<Invoices>();
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("currentTotal");
        q.setParameter("iid", id);
        list = q.getResultList();
        return list;
    }

    public List<Invoices> getInvoices(PatientRecord prid) {
        List<Invoices> list = new ArrayList<Invoices>();
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("getInvoice");
        q.setParameter("prid", prid);
        list = q.getResultList();
        return list;
    }

    public List<Invoices> getInvoice(BiillingPoint bp, Date dt, int authorised) {
        List<Invoices> list = new ArrayList<Invoices>();
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("getInvoices");
        q.setParameter("bp", bp);
        q.setParameter("dt", dt);
        q.setParameter("au", authorised);
        list = q.getResultList();
        return list;
    }

    public List<Invoices> getInvoiceByDuration(BiillingPoint bp, Date sdt, Date edt, int authorised) {
        List<Invoices> list = new ArrayList<Invoices>();
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("getInvoicesDuration");
        q.setParameter("bp", bp);
        q.setParameter("sdt", sdt);
        q.setParameter("edt", edt);
        q.setParameter("au", authorised);
        list = q.getResultList();
        return list;
    }

    public List<Invoices> getAllInvoicesOnBillingPointDuration(BiillingPoint bp, Date sdt, Date edt) {
        List<Invoices> list = new ArrayList<Invoices>();
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("getAllInvoicesOnBillingPointDuration");
        q.setParameter("bp", bp);
        q.setParameter("sdt", sdt);
        q.setParameter("edt", edt);
        list = q.getResultList();
        return list;
    }

    public List<Invoices> getAllInvoicesForToday(BiillingPoint bp, Date sdt) {
        List<Invoices> list = new ArrayList<Invoices>();
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("getAllInvoicesForToday");
        q.setParameter("bp", bp);
        q.setParameter("sdt", sdt);
        list = q.getResultList();
        return list;
    }

    public List<Invoices> getAllInvoicesForTodayByUser(BiillingPoint bp, Uusers user, Date sdt, int au) {
        List<Invoices> list = new ArrayList<Invoices>();
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("getAllInvoicesForTodayByUser");
        q.setParameter("bp", bp);
        q.setParameter("sdt", sdt);
        q.setParameter("user", user);
        q.setParameter("au", au);
        list = q.getResultList();
        return list;
    }

    public List<Invoices> getAllInvoicesForDurationByUser(BiillingPoint bp, Uusers user, Date sdt, Date edt, int au) {
        List<Invoices> list = new ArrayList<Invoices>();
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("getAllInvoicesForDurationByUser");
        q.setParameter("bp", bp);
        q.setParameter("sdt", sdt);
        q.setParameter("edt", edt);
        q.setParameter("user", user);
        q.setParameter("au", au);
        list = q.getResultList();
        return list;
    }

    public List<Invoices> getAllInvoicesOnBillingPointByUser(BiillingPoint bp, Uusers user, Date sdt, Date edt) {
        List<Invoices> list = new ArrayList<Invoices>();
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("getAllInvoicesOnBillingPointByUser");
        q.setParameter("bp", bp);
        q.setParameter("sdt", sdt);
        q.setParameter("edt", edt);
        q.setParameter("user", user);
        list = q.getResultList();
        return list;
    }

    public List<Invoices> getAllInvoicesForTodayOnBillingPointByUser(BiillingPoint bp, Uusers user, Date sdt) {
        List<Invoices> list = new ArrayList<Invoices>();
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("getAllInvoicesForTodayOnBillingPointByUser");
        q.setParameter("bp", bp);
        q.setParameter("sdt", sdt);
        q.setParameter("user", user);
        list = q.getResultList();
        return list;
    }

    public void create(Invoices invoices) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(invoices);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Invoices invoices) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            invoices = em.merge(invoices);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = invoices.getId();
                if (findInvoices(id) == null) {
                    throw new NonexistentEntityException("The invoices with id " + id + " no longer exists.");
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
            Invoices invoices;
            try {
                invoices = em.getReference(Invoices.class, id);
                invoices.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The invoices with id " + id + " no longer exists.", enfe);
            }
            em.remove(invoices);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Invoices> findInvoicesEntities() {
        return findInvoicesEntities(true, -1, -1);
    }

    public List<Invoices> findInvoicesEntities(int maxResults, int firstResult) {
        return findInvoicesEntities(false, maxResults, firstResult);
    }

    private List<Invoices> findInvoicesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Invoices.class));
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

    public Invoices findInvoices(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Invoices.class, id);
        } finally {
            em.close();
        }
    }

    public int getInvoicesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Invoices> rt = cq.from(Invoices.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
