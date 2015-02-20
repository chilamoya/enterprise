/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.management.persistence;

import com.innate.cresterp.management.entities.Program;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.innate.cresterp.management.entities.ProgramItem;
import com.innate.cresterp.persistence.management.exceptions.NonexistentEntityException;
import com.innate.cresterp.insurance.risk.persistence.Configuration;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Tafadzwa
 */
public class ProgramJpaController implements Serializable {

    public ProgramJpaController(EntityManagerFactory emf) {
         this.emf = new Configuration().generateEntityManagerFactory();

    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Program program) {
        if (program.getItem() == null) {
            program.setItem(new ArrayList<ProgramItem>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ProgramItem> attachedItem = new ArrayList<ProgramItem>();
            for (ProgramItem itemProgramItemToAttach : program.getItem()) {
                itemProgramItemToAttach = em.getReference(itemProgramItemToAttach.getClass(), itemProgramItemToAttach.getId());
                attachedItem.add(itemProgramItemToAttach);
            }
            program.setItem(attachedItem);
            em.persist(program);
            for (ProgramItem itemProgramItem : program.getItem()) {
                Program oldProgramOfItemProgramItem = itemProgramItem.getProgram();
                itemProgramItem.setProgram(program);
                itemProgramItem = em.merge(itemProgramItem);
                if (oldProgramOfItemProgramItem != null) {
                    oldProgramOfItemProgramItem.getItem().remove(itemProgramItem);
                    oldProgramOfItemProgramItem = em.merge(oldProgramOfItemProgramItem);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Program program) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Program persistentProgram = em.find(Program.class, program.getId());
            List<ProgramItem> itemOld = persistentProgram.getItem();
            List<ProgramItem> itemNew = program.getItem();
            List<ProgramItem> attachedItemNew = new ArrayList<ProgramItem>();
            for (ProgramItem itemNewProgramItemToAttach : itemNew) {
                itemNewProgramItemToAttach = em.getReference(itemNewProgramItemToAttach.getClass(), itemNewProgramItemToAttach.getId());
                attachedItemNew.add(itemNewProgramItemToAttach);
            }
            itemNew = attachedItemNew;
            program.setItem(itemNew);
            program = em.merge(program);
            for (ProgramItem itemOldProgramItem : itemOld) {
                if (!itemNew.contains(itemOldProgramItem)) {
                    itemOldProgramItem.setProgram(null);
                    itemOldProgramItem = em.merge(itemOldProgramItem);
                }
            }
            for (ProgramItem itemNewProgramItem : itemNew) {
                if (!itemOld.contains(itemNewProgramItem)) {
                    Program oldProgramOfItemNewProgramItem = itemNewProgramItem.getProgram();
                    itemNewProgramItem.setProgram(program);
                    itemNewProgramItem = em.merge(itemNewProgramItem);
                    if (oldProgramOfItemNewProgramItem != null && !oldProgramOfItemNewProgramItem.equals(program)) {
                        oldProgramOfItemNewProgramItem.getItem().remove(itemNewProgramItem);
                        oldProgramOfItemNewProgramItem = em.merge(oldProgramOfItemNewProgramItem);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = program.getId();
                if (findProgram(id) == null) {
                    throw new NonexistentEntityException("The program with id " + id + " no longer exists.");
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
            Program program;
            try {
                program = em.getReference(Program.class, id);
                program.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The program with id " + id + " no longer exists.", enfe);
            }
            List<ProgramItem> item = program.getItem();
            for (ProgramItem itemProgramItem : item) {
                itemProgramItem.setProgram(null);
                itemProgramItem = em.merge(itemProgramItem);
            }
            em.remove(program);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Program> findProgramEntities() {
        return findProgramEntities(true, -1, -1);
    }

    public List<Program> findProgramEntities(int maxResults, int firstResult) {
        return findProgramEntities(false, maxResults, firstResult);
    }

    private List<Program> findProgramEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Program.class));
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

    public Program findProgram(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Program.class, id);
        } finally {
            em.close();
        }
    }

    public int getProgramCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Program> rt = cq.from(Program.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
