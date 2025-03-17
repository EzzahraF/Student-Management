package com.ensa.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    private static final EntityManagerFactory entityManagerFactory;

    static {
        EntityManagerFactory tempFactory = null;
        try {
            tempFactory = Persistence.createEntityManagerFactory("mapersistence");
        } catch (Throwable ex) {
            ex.printStackTrace(); // Affichage détaillé de l'erreur
            throw new ExceptionInInitializerError("Initial EntityManagerFactory creation failed: " + ex.getMessage());
        }
        entityManagerFactory = tempFactory;

        // Fermer l'EntityManagerFactory à la fermeture de l'application
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
                entityManagerFactory.close();
            }
        }));
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
