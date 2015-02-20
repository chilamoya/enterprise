/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.management.persistence;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.innate.cresterp.management.entities.Program;
import com.innate.cresterp.management.entities.ProgramItem;
import com.innate.cresterp.persistence.management.exceptions.NonexistentEntityException;
import com.innate.cresterp.insurance.risk.persistence.Configuration;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Tafadzwa
 */
public class ProgramItemJpaController implements Serializable {

    public ProgramItemJpaController(EntityManagerFactory emf) {
         this.emf = new Configuration().generateEntityManagerFactory();

    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProgramItem programItem) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Program program = programItem.getProgram();
            if (program != null) {
                program = em.getReference(program.getClass(), program.getId());
                programItem.setProgram(program);
            }
            em.persist(programItem);
            if (program != null) {
                program.getItem().add(programItem);
                program = em.merge(program);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProgramItem programItem) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProgramItem persistentProgramItem = em.find(ProgramItem.class, programItem.getId());
            Program programOld = persistentProgramItem.getProgram();
            Program programNew = programItem.getProgram();
            if (programNew != null) {
                programNew = em.getReference(programNew.getClass(), programNew.getId());
                programItem.setProgram(programNew);
            }
            programItem = em.merge(programItem);
            if (programOld != null && !programOld.equals(programNew)) {
                programOld.getItem().remove(programItem);
                programOld = em.merge(programOld);
            }
            if (programNew != null && !programNew.equals(programOld)) {
                programNew.getItem().add(programItem);
                programNew = em.merge(programNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = programItem.getId();
                if (findProgramItem(id) == null) {
                    throw new NonexistentEntityException("The programItem with id " + id + " no longer exists.");
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
            ProgramItem programItem;
            try {
                programItem = em.getReference(ProgramItem.class, id);
                programItem.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The programItem with id " + id + " no longer exists.", enfe);
            }
            Program program = programItem.getProgram();
            if (program != null) {
                program.getItem().remove(programItem);
                program = em.merge(program);
            }
            em.remove(programItem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProgramItem> findProgramItemEntities() {
        return findProgramItemEntities(true, -1, -1);
    }

    public List<ProgramItem> findProgramItemEntities(int maxResults, int firstResult) {
        return findProgramItemEntities(false, maxResults, firstResult);
    }

    private List<ProgramItem> findProgramItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProgramItem.class));
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

    public ProgramItem findProgramItem(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProgramItem.class, id);
        } finally {
            em.close();
        }
    }

    public int getProgramItemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProgramItem> rt = cq.from(ProgramItem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
