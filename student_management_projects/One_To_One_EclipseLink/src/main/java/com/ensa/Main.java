package com.ensa;

import com.ensa.Entity.Adresse;
import com.ensa.Entity.Student;
import com.ensa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Créer une instance d'Adresse
        Adresse adresse = new Adresse();
        adresse.setRue("Rue de l'exemple");
        adresse.setVille("Marrakech");
        adresse.setCodePostal("40000");

        // Créer une instance de Student et lui affecter une adresse
        Student student = new Student();
        student.setName("Nom de l'étudiant");
        student.setAdresse(adresse);

        // Persister l'entité
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(adresse); // Persister l'adresse
            em.persist(student);  // Persister l'étudiant
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

        // Récupérer les étudiants avec leurs adresses
        EntityManager em2 = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            // Créer une requête pour récupérer tous les étudiants
            TypedQuery<Student> query = em2.createQuery("SELECT s FROM Student s", Student.class);
            List<Student> students = query.getResultList();

            // Afficher les données dans un format de table
            System.out.println("ID\tNom\tRue\tVille\tCode Postal");
            for (Student s : students) {
                Adresse adr = s.getAdresse(); // Récupérer l'adresse de l'étudiant
                System.out.println(s.getId() + "\t" + s.getName() + "\t" +
                        adr.getRue() + "\t" + adr.getVille() + "\t" + adr.getCodePostal());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em2.close();
        }
    }
}
