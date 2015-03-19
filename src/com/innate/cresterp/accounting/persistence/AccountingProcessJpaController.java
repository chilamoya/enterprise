/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.accounting.persistence;

import com.innate.cresterp.accounting.entities.AccountingProcessDefinition;
import com.innate.cresterp.accounting.persistence.exceptions.NonexistentEntityException;
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
 * @author user
 */
public class AccountingProcessJpaController implements Serializable {

    public AccountingProcessJpaController(EntityManagerFactory emf) {
        
         this.emf = new Configuration().generateEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AccountingProcessDefinition accountingProcess) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(accountingProcess);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AccountingProcessDefinition accountingProcess) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            accountingProcess = em.merge(accountingProcess);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = accountingProcess.getId();
                if (findAccountingProcess(id) == null) {
                    throw new NonexistentEntityException("The accountingProcess with id " + id + " no longer exists.");
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
            AccountingProcessDefinition accountingProcess;
            try {
                accountingProcess = em.getReference(AccountingProcessDefinition.class, id);
                accountingProcess.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accountingProcess with id " + id + " no longer exists.", enfe);
            }
            em.remove(accountingProcess);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AccountingProcessDefinition> findAccountingProcessEntities() {
        return findAccountingProcessEntities(true, -1, -1);
    }

    public List<AccountingProcessDefinition> findAccountingProcessEntities(int maxResults, int firstResult) {
        return findAccountingProcessEntities(false, maxResults, firstResult);
    }

    private List<AccountingProcessDefinition> findAccountingProcessEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AccountingProcessDefinition.class));
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

    public AccountingProcessDefinition findAccountingProcess(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AccountingProcessDefinition.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccountingProcessCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AccountingProcessDefinition> rt = cq.from(AccountingProcessDefinition.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
