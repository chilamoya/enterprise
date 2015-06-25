/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.communication.persistence;

import com.innate.cresterp.communication.entities.SMSMessage;
import com.innate.cresterp.insurance.risk.exceptions.NonexistentEntityException;
import com.innate.cresterp.insurance.risk.persistence.Configuration;
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
public class SMSMessageJpaController implements Serializable {

    public SMSMessageJpaController() {
        this.emf = new Configuration().generateEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SMSMessage SMSMessage) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(SMSMessage);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SMSMessage SMSMessage) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SMSMessage = em.merge(SMSMessage);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = SMSMessage.getId();
                if (findSMSMessage(id) == null) {
                    throw new NonexistentEntityException("The sMSMessage with id " + id + " no longer exists.");
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
            SMSMessage SMSMessage;
            try {
                SMSMessage = em.getReference(SMSMessage.class, id);
                SMSMessage.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The SMSMessage with id " + id + " no longer exists.", enfe);
            }
            em.remove(SMSMessage);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SMSMessage> findSMSMessageEntities() {
        return findSMSMessageEntities(true, -1, -1);
    }

    public List<SMSMessage> findSMSMessageEntities(int maxResults, int firstResult) {
        return findSMSMessageEntities(false, maxResults, firstResult);
    }

    private List<SMSMessage> findSMSMessageEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SMSMessage.class));
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

    public SMSMessage findSMSMessage(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SMSMessage.class, id);
        } finally {
            em.close();
        }
    }

    public int getSMSMessageCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SMSMessage> rt = cq.from(SMSMessage.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
