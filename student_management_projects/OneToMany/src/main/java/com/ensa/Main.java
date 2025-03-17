package com.ensa;

import com.ensa.Entity.Adresse;
import com.ensa.Entity.Teacher;
import com.ensa.Entity.Module;
import com.ensa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Création d'une adresse
        Adresse adresse = new Adresse();
        adresse.setRue("Rue de l'exemple");
        adresse.setVille("Marrakech");
        adresse.setCodePostal("40000");

        // Création d'un enseignant
        Teacher teacher = new Teacher();
        teacher.setName("Nom de l'enseignant");
        teacher.setAdresse(adresse);

        // Création de modules pour l'enseignant
        Module module1 = new Module();
        module1.setModuleName("Mathématiques");
        module1.setTeacher(teacher);  // Associer le module à l'enseignant

        Module module2 = new Module();
        module2.setModuleName("Informatique");
        module2.setTeacher(teacher);

        List<Module> modules = new ArrayList<>();
        modules.add(module1);
        modules.add(module2);

        teacher.setModules(modules);  // Ajouter les modules à l'enseignant

        // Persistance des entités
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(adresse);  // Sauvegarder l'adresse
            em.persist(teacher);  // Sauvegarder l'enseignant
            em.persist(module1);  // Sauvegarder le module 1
            em.persist(module2);  // Sauvegarder le module 2
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

        // Récupération et affichage des enseignants avec leurs adresses et modules
        EntityManager em2 = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            // Récupérer tous les enseignants
            TypedQuery<Teacher> query = em2.createQuery("SELECT t FROM Teacher t", Teacher.class);
            List<Teacher> teachers = query.getResultList();

            // Afficher les données dans un format structuré
            System.out.println("ID\tNom\tRue\tVille\tCode Postal\tModules");
            for (Teacher t : teachers) {
                Adresse adr = t.getAdresse(); // Récupérer l'adresse de l'enseignant
                System.out.print(t.getId() + "\t" + t.getName() + "\t" +
                        adr.getRue() + "\t" + adr.getVille() + "\t" + adr.getCodePostal() + "\t");

                // Afficher les modules de l'enseignant
                List<Module> teacherModules = t.getModules();
                for (Module m : teacherModules) {
                    System.out.print(m.getModuleName() + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em2.close();
        }
    }
}
