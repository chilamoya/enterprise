/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.management.persistence;

import com.innate.cresterp.management.entities.Question;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.innate.cresterp.management.entities.Questionnaire;
import com.innate.cresterp.persistence.management.exceptions.NonexistentEntityException;
import com.innate.cresterp.insurance.risk.persistence.Configuration;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Tafadzwa
 */
public class QuestionJpaController implements Serializable {

    public QuestionJpaController(EntityManagerFactory emf) {
        this.emf = new Configuration().generateEntityManagerFactory();

    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Question question) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Questionnaire questionnaire = question.getQuestionnaire();
            if (questionnaire != null) {
                questionnaire = em.getReference(questionnaire.getClass(), questionnaire.getId());
                question.setQuestionnaire(questionnaire);
            }
            em.persist(question);
            if (questionnaire != null) {
                questionnaire.getQuestions().add(question);
                questionnaire = em.merge(questionnaire);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Question question) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Question persistentQuestion = em.find(Question.class, question.getId());
            Questionnaire questionnaireOld = persistentQuestion.getQuestionnaire();
            Questionnaire questionnaireNew = question.getQuestionnaire();
            if (questionnaireNew != null) {
                questionnaireNew = em.getReference(questionnaireNew.getClass(), questionnaireNew.getId());
                question.setQuestionnaire(questionnaireNew);
            }
            question = em.merge(question);
            if (questionnaireOld != null && !questionnaireOld.equals(questionnaireNew)) {
                questionnaireOld.getQuestions().remove(question);
                questionnaireOld = em.merge(questionnaireOld);
            }
            if (questionnaireNew != null && !questionnaireNew.equals(questionnaireOld)) {
                questionnaireNew.getQuestions().add(question);
                questionnaireNew = em.merge(questionnaireNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = question.getId();
                if (findQuestion(id) == null) {
                    throw new NonexistentEntityException("The question with id " + id + " no longer exists.");
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
            Question question;
            try {
                question = em.getReference(Question.class, id);
                question.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The question with id " + id + " no longer exists.", enfe);
            }
            Questionnaire questionnaire = question.getQuestionnaire();
            if (questionnaire != null) {
                questionnaire.getQuestions().remove(question);
                questionnaire = em.merge(questionnaire);
            }
            em.remove(question);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Question> findQuestionEntities() {
        return findQuestionEntities(true, -1, -1);
    }

    public List<Question> findQuestionEntities(int maxResults, int firstResult) {
        return findQuestionEntities(false, maxResults, firstResult);
    }

    private List<Question> findQuestionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Question.class));
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

    public Question findQuestion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Question.class, id);
        } finally {
            em.close();
        }
    }

    public int getQuestionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Question> rt = cq.from(Question.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
