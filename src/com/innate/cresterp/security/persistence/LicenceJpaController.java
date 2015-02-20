/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.security.persistence;

import com.innate.cresterp.security.entities.Licence;
import com.innate.cresterp.insurance.risk.persistence.Configuration;
import com.innate.cresterp.insurance.risk.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Tafadzwa
 */
public class LicenceJpaController implements Serializable {

    public LicenceJpaController(EntityManagerFactory emf) {
             this.emf = new Configuration().generateEntityManagerFactory();

    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Licence licence) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(licence);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Licence licence) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            licence = em.merge(licence);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = licence.getId();
                if (findLicence(id) == null) {
                    throw new NonexistentEntityException("The licence with id " + id + " no longer exists.");
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
            Licence licence;
            try {
                licence = em.getReference(Licence.class, id);
                licence.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The licence with id " + id + " no longer exists.", enfe);
            }
            em.remove(licence);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Licence> findLicenceEntities() {
        return findLicenceEntities(true, -1, -1);
    }

    public List<Licence> findLicenceEntities(int maxResults, int firstResult) {
        return findLicenceEntities(false, maxResults, firstResult);
    }

    private List<Licence> findLicenceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Licence.class));
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

    public Licence findLicence(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Licence.class, id);
        } finally {
            em.close();
        }
    }

    public int getLicenceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Licence> rt = cq.from(Licence.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
