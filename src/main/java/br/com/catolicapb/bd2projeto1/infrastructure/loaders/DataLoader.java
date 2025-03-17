package br.com.catolicapb.bd2projeto1.infrastructure.loaders;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DataLoader {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sistema-biblioteca");

    public static EntityManager getEntityManager() { return emf.createEntityManager();}
}
