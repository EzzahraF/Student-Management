package com.ensa;

import com.ensa.Entity.Student;
import com.ensa.Entity.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import com.ensa.utils.JpaUtil;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // Créer des étudiants
        Student student1 = new Student("John Doe");
        Student student2 = new Student("Jane Smith");

        // Créer des cours
        Course course1 = new Course("Maths");
        Course course2 = new Course("Informatics");

        // Ajouter les cours aux étudiants
        student1.getCourses().add(course1);
        student1.getCourses().add(course2);

        student2.getCourses().add(course2);

        // Ajouter les étudiants aux cours
        course1.getStudents().add(student1);
        course2.getStudents().add(student1);
        course2.getStudents().add(student2);

        // Persister les entités dans la base de données
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            em.persist(student1);
            em.persist(student2);
            em.persist(course1);
            em.persist(course2);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

        // Récupérer les étudiants et afficher leurs cours
        EntityManager em2 = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Student> query = em2.createQuery("SELECT s FROM Student s", Student.class);
            Set<Student> students = Set.copyOf(query.getResultList());

            for (Student student : students) {
                System.out.println("Student: " + student.getName());
                System.out.println("Courses: ");
                for (Course course : student.getCourses()) {
                    System.out.println(course.getTitle());
                }
                System.out.println();
            }
        } finally {
            em2.close();
        }
    }
}
