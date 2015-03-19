/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.medical.hospital.persistence;

import com.innate.cresterp.exceptions.NonexistentEntityException;
import com.innate.cresterp.medical.hospital.entities.Waard;
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
public class WaardJpaController {

    public WaardJpaController() {
        emf = Persistence.createEntityManagerFactory("DebtorsDemoPersistancePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Waard waard) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(waard);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Waard waard) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            waard = em.merge(waard);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = waard.getId();
                if (findWaard(id) == null) {
                    throw new NonexistentEntityException("The waard with id " + id + " no longer exists.");
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
            Waard waard;
            try {
                waard = em.getReference(Waard.class, id);
                waard.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The waard with id " + id + " no longer exists.", enfe);
            }
            em.remove(waard);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Waard> findWaardEntities() {
        return findWaardEntities(true, -1, -1);
    }

    public List<Waard> findWaardEntities(int maxResults, int firstResult) {
        return findWaardEntities(false, maxResults, firstResult);
    }

    private List<Waard> findWaardEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Waard.class));
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

    public Waard findWaard(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Waard.class, id);
        } finally {
            em.close();
        }
    }

    public int getWaardCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Waard> rt = cq.from(Waard.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
