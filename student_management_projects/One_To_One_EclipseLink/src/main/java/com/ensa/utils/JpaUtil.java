package com.ensa.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    private static final EntityManagerFactory entityManagerFactory;

    // Initialisation statique du EntityManagerFactory
    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("mapersistence");
        } catch (Throwable ex) {
            System.err.println("Initial EntityManagerFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Méthode pour récupérer l'EntityManagerFactory
    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    // Méthode pour obtenir un EntityManager à partir de l'EntityManagerFactory
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    // Méthode pour fermer l'EntityManagerFactory lorsque l'application se termine
    public static void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}


