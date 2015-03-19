/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.medical.hospital.persistence;

import com.innate.cresterp.exceptions.NonexistentEntityException;
import com.innate.cresterp.medical.hospital.entities.Aadmissions;
import com.innate.cresterp.medical.hospital.entities.Biiills;
import com.innate.cresterp.medical.hospital.entities.Invoices;
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
public class BiiillsJpaController {

    public BiiillsJpaController() {
        emf = Persistence.createEntityManagerFactory("DebtorsDemoPersistancePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

//      public List<Biiills> getBillsBy( Aadmissions admin){
//
//        List<Biiills> list = new ArrayList<Biiills>();
//        EntityManager em = emf.createEntityManager();
//        Query q = em.createNamedQuery("getBill");
//
//
//        q.setParameter("ad", admin);
//        list = q.getResultList();
//
//
//       return list;
//    }
//REVISE CALCULATING OVERALL BILL
    public List<Biiills> getBill(Invoices in) {

        List<Biiills> list = new ArrayList<Biiills>();
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("getBill");

        q.setParameter("in", in);
        list = q.getResultList();


        return list;

    }

    public List<Biiills> getAll() {

        List<Biiills> list = new ArrayList<Biiills>();
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("getAll");

//        q.setParameter("pt", pr);
        list = q.getResultList();


        return list;
    }

    public void deleteBill(Biiills bills) {

        EntityManager em = emf.createEntityManager();
//        Query q = em.createNamedQuery("deleteBill");
//        q.setParameter("bill", bills.getId());
//        q.executeUpdate();
        em.remove(em.merge(bills));
    }

    public void create(Biiills biiills) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(biiills);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Biiills biiills) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            biiills = em.merge(biiills);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = biiills.getId();
                if (findBiiills(id) == null) {
                    throw new NonexistentEntityException("The biiills with id " + id + " no longer exists.");
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

            Biiills biiills;
            try {
                biiills = em.getReference(Biiills.class, id);
                biiills.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The biiills with id " + id + " no longer exists.", enfe);
            }
            em.remove(biiills);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Biiills> findBiiillsEntities() {
        return findBiiillsEntities(true, -1, -1);
    }

    public List<Biiills> findBiiillsEntities(int maxResults, int firstResult) {
        return findBiiillsEntities(false, maxResults, firstResult);
    }

    private List<Biiills> findBiiillsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Biiills.class));
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

    public Biiills findBiiills(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Biiills.class, id);
        } finally {
            em.close();
        }
    }

    public int getBiiillsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Biiills> rt = cq.from(Biiills.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
