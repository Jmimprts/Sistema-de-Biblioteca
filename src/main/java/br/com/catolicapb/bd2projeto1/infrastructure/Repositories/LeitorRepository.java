package br.com.catolicapb.bd2projeto1.infrastructure.Repositories;

import br.com.catolicapb.bd2projeto1.entity.Leitor;
import br.com.catolicapb.bd2projeto1.infrastructure.loaders.DataLoader;
import jakarta.persistence.EntityManager;

public class LeitorRepository {
    public void addLeitor(Leitor leitor) {
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();
        em.persist(leitor);
        em.getTransaction().commit();
        em.close();
    }
}
