package com.ensa.dao;


import com.ensa.Entity.Student;
import com.ensa.utils.JpaUtil;
import jakarta.persistence.EntityManager;

public class StudentDAO {

    public void saveStudent(Student student) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(student);  // Persister l'entité Student
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Student findStudentById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Student.class, id);  // Récupérer l'entité Student par son ID
        } finally {
            em.close();
        }
    }

    public void updateStudent(Student student) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(student);  // Mettre à jour l'entité Student
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void deleteStudent(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, id);
            if (student != null) {
                em.remove(student);  // Supprimer l'entité Student
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
