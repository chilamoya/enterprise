/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.insurance.risk.persistence;

import com.innate.cresterp.insurance.risk.exceptions.NonexistentEntityException;
import com.innate.cresterp.medical.hospital.entities.PatientRecord;
import com.innate.cresterp.insurance.risk.entities.PolicyPeriod;
import com.innate.cresterp.insurance.risk.entities.PolicyType;
import com.innate.cresterp.insurance.risk.entities.Risk;
import com.innate.cresterp.insurance.risk.entities.RiskPremium;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
 
/**
 *
 * @author Tafadzwa
 */
public class RiskJpaController implements Serializable {

    public RiskJpaController() {
     
        this.emf = new Configuration().generateEntityManagerFactory();
  
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

  
    public List<Risk> findRiskByClient(PatientRecord client){
        
        List<Risk> clientRisks =  new ArrayList<Risk>();
        
        for (Risk risk: findRiskEntities()){
            if (risk.getClient().equals(client))
                clientRisks.add(risk);
        }
        
        
        return clientRisks;
    }
    
    public void create(Risk risk) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
           
            // Persist Premium
            RiskPremiumJpaController rpManager =  new RiskPremiumJpaController();
            RiskPremium rp = risk.getRiskPremium();
            rpManager.create(rp);
            
            //Persist Policy Period 
            PolicyPeriodJpaController ppManager = new PolicyPeriodJpaController();
            PolicyPeriod pp = risk.getPolicyPeriod();
            ppManager.create(pp);
            
            //Persist Policy Type 
            PolicyTypeJpaController ptManager =  new PolicyTypeJpaController();
            PolicyType pt = risk.getPolicyType();
            ptManager.create(pt);
            
         
            //Update the Risk entity 
            risk.setRiskPremium(rp);
            risk.setPolicyPeriod(pp);
            risk.setPolicyType(pt);
            //Persist the entity with the udpdated values 
            
            em.persist(risk);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Risk risk) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            risk = em.merge(risk);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = risk.getId();
                if (findRisk(id) == null) {
                    throw new NonexistentEntityException("The risk with id " + id + " no longer exists.");
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
            Risk risk;
            try {
                risk = em.getReference(Risk.class, id);
                risk.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The risk with id " + id + " no longer exists.", enfe);
            }
            em.remove(risk);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Risk> findRiskEntities() {
        return findRiskEntities(true, -1, -1);
    }

    public List<Risk> findRiskEntities(int maxResults, int firstResult) {
        return findRiskEntities(false, maxResults, firstResult);
    }

    private List<Risk> findRiskEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Risk.class));
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

    public Risk findRisk(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Risk.class, id);
        } finally {
            em.close();
        }
    }

    public int getRiskCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Risk> rt = cq.from(Risk.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
