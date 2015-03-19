/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.medical.hospital.persistence;

import com.innate.cresterp.exceptions.NonexistentEntityException;
import com.innate.cresterp.medical.hospital.entities.Roooms;
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
public class RooomsJpaController {

    public RooomsJpaController() {
        emf = Persistence.createEntityManagerFactory("DebtorsDemoPersistancePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Roooms roooms) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(roooms);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Roooms roooms) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            roooms = em.merge(roooms);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = roooms.getId();
                if (findRoooms(id) == null) {
                    throw new NonexistentEntityException("The roooms with id " + id + " no longer exists.");
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
            Roooms roooms;
            try {
                roooms = em.getReference(Roooms.class, id);
                roooms.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The roooms with id " + id + " no longer exists.", enfe);
            }
            em.remove(roooms);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Roooms> findRooomsEntities() {
        return findRooomsEntities(true, -1, -1);
    }

    public List<Roooms> findRooomsEntities(int maxResults, int firstResult) {
        return findRooomsEntities(false, maxResults, firstResult);
    }

    private List<Roooms> findRooomsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Roooms.class));
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

    public Roooms findRoooms(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Roooms.class, id);
        } finally {
            em.close();
        }
    }

    public int getRooomsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Roooms> rt = cq.from(Roooms.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
