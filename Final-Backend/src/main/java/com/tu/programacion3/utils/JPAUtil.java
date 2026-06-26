package com.tu.programacion3.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    // Única instancia de EntityManagerFactory
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("miUnidad");

    // Devuelve la fábrica de EntityManager
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    // Cierra la fábrica al finalizar el programa
    public static void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}