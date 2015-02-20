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
import com.innate.cresterp.management.entities.Question;
import com.innate.cresterp.management.entities.Questionnaire;
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
public class QuestionnaireJpaController implements Serializable {

    public QuestionnaireJpaController(EntityManagerFactory emf) {
         this.emf = new Configuration().generateEntityManagerFactory();

    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Questionnaire questionnaire) {
        if (questionnaire.getQuestions() == null) {
            questionnaire.setQuestions(new ArrayList<Question>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Question> attachedQuestions = new ArrayList<Question>();
            for (Question questionsQuestionToAttach : questionnaire.getQuestions()) {
                questionsQuestionToAttach = em.getReference(questionsQuestionToAttach.getClass(), questionsQuestionToAttach.getId());
                attachedQuestions.add(questionsQuestionToAttach);
            }
            questionnaire.setQuestions(attachedQuestions);
            em.persist(questionnaire);
            for (Question questionsQuestion : questionnaire.getQuestions()) {
                Questionnaire oldQuestionnaireOfQuestionsQuestion = questionsQuestion.getQuestionnaire();
                questionsQuestion.setQuestionnaire(questionnaire);
                questionsQuestion = em.merge(questionsQuestion);
                if (oldQuestionnaireOfQuestionsQuestion != null) {
                    oldQuestionnaireOfQuestionsQuestion.getQuestions().remove(questionsQuestion);
                    oldQuestionnaireOfQuestionsQuestion = em.merge(oldQuestionnaireOfQuestionsQuestion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Questionnaire questionnaire) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Questionnaire persistentQuestionnaire = em.find(Questionnaire.class, questionnaire.getId());
            List<Question> questionsOld = persistentQuestionnaire.getQuestions();
            List<Question> questionsNew = questionnaire.getQuestions();
            List<Question> attachedQuestionsNew = new ArrayList<Question>();
            for (Question questionsNewQuestionToAttach : questionsNew) {
                questionsNewQuestionToAttach = em.getReference(questionsNewQuestionToAttach.getClass(), questionsNewQuestionToAttach.getId());
                attachedQuestionsNew.add(questionsNewQuestionToAttach);
            }
            questionsNew = attachedQuestionsNew;
            questionnaire.setQuestions(questionsNew);
            questionnaire = em.merge(questionnaire);
            for (Question questionsOldQuestion : questionsOld) {
                if (!questionsNew.contains(questionsOldQuestion)) {
                    questionsOldQuestion.setQuestionnaire(null);
                    questionsOldQuestion = em.merge(questionsOldQuestion);
                }
            }
            for (Question questionsNewQuestion : questionsNew) {
                if (!questionsOld.contains(questionsNewQuestion)) {
                    Questionnaire oldQuestionnaireOfQuestionsNewQuestion = questionsNewQuestion.getQuestionnaire();
                    questionsNewQuestion.setQuestionnaire(questionnaire);
                    questionsNewQuestion = em.merge(questionsNewQuestion);
                    if (oldQuestionnaireOfQuestionsNewQuestion != null && !oldQuestionnaireOfQuestionsNewQuestion.equals(questionnaire)) {
                        oldQuestionnaireOfQuestionsNewQuestion.getQuestions().remove(questionsNewQuestion);
                        oldQuestionnaireOfQuestionsNewQuestion = em.merge(oldQuestionnaireOfQuestionsNewQuestion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = questionnaire.getId();
                if (findQuestionnaire(id) == null) {
                    throw new NonexistentEntityException("The questionnaire with id " + id + " no longer exists.");
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
            Questionnaire questionnaire;
            try {
                questionnaire = em.getReference(Questionnaire.class, id);
                questionnaire.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The questionnaire with id " + id + " no longer exists.", enfe);
            }
            List<Question> questions = questionnaire.getQuestions();
            for (Question questionsQuestion : questions) {
                questionsQuestion.setQuestionnaire(null);
                questionsQuestion = em.merge(questionsQuestion);
            }
            em.remove(questionnaire);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Questionnaire> findQuestionnaireEntities() {
        return findQuestionnaireEntities(true, -1, -1);
    }

    public List<Questionnaire> findQuestionnaireEntities(int maxResults, int firstResult) {
        return findQuestionnaireEntities(false, maxResults, firstResult);
    }

    private List<Questionnaire> findQuestionnaireEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Questionnaire.class));
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

    public Questionnaire findQuestionnaire(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Questionnaire.class, id);
        } finally {
            em.close();
        }
    }

    public int getQuestionnaireCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Questionnaire> rt = cq.from(Questionnaire.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
