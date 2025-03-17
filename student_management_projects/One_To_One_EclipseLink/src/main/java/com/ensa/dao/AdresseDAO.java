package com.ensa.dao;


import com.ensa.Entity.Adresse;
import com.ensa.utils.JpaUtil;
import jakarta.persistence.EntityManager;

public class AdresseDAO {

    public void saveAdresse(Adresse adresse) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(adresse);  // Persister l'entité Adresse
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Adresse findAdresseById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Adresse.class, id);  // Récupérer l'entité Adresse par son ID
        } finally {
            em.close();
        }
    }

    public void updateAdresse(Adresse adresse) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(adresse);  // Mettre à jour l'entité Adresse
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void deleteAdresse(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Adresse adresse = em.find(Adresse.class, id);
            if (adresse != null) {
                em.remove(adresse);  // Supprimer l'entité Adresse
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
