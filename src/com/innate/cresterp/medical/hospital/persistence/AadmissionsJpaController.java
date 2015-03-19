/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.medical.hospital.persistence;

import com.innate.cresterp.exceptions.NonexistentEntityException;
import com.innate.cresterp.medical.hospital.entities.Aadmissions;
import com.innate.cresterp.medical.hospital.entities.Beed;
import com.innate.cresterp.medical.hospital.entities.PatientRecord;
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
public class AadmissionsJpaController {

    public AadmissionsJpaController() {
        emf = Persistence.createEntityManagerFactory("DebtorsDemoPersistancePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public List<Aadmissions> getAdmissions(PatientRecord pr ){
        List<Aadmissions> lst = new ArrayList<Aadmissions>();
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("getAdmissions");
        q.setParameter("id", pr);
        lst = q.getResultList();
        return lst;
    }

    public int editBed(Beed b){
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("updateBed");
        q.setParameter("b", b);
        return q.executeUpdate();
    }


    public void create(Aadmissions aadmissions) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(aadmissions);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Aadmissions aadmissions) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            aadmissions = em.merge(aadmissions);
            em.getTransaction().commit();
           
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = aadmissions.getId();
                if (findAadmissions(id) == null) {
                    throw new NonexistentEntityException("The aadmissions with id " + id + " no longer exists.");
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
            Aadmissions aadmissions;
            try {
                aadmissions = em.getReference(Aadmissions.class, id);
                aadmissions.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aadmissions with id " + id + " no longer exists.", enfe);
            }
            em.remove(aadmissions);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Aadmissions> findAadmissionsEntities() {
        return findAadmissionsEntities(true, -1, -1);
    }

    public List<Aadmissions> findAadmissionsEntities(int maxResults, int firstResult) {
        return findAadmissionsEntities(false, maxResults, firstResult);
    }

    private List<Aadmissions> findAadmissionsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Aadmissions.class));
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

    public Aadmissions findAadmissions(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Aadmissions.class, id);
        } finally {
            em.close();
        }
    }

    public int getAadmissionsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Aadmissions> rt = cq.from(Aadmissions.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
