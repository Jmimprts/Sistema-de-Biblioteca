package br.com.catolicapb.bd2projeto1.infrastructure.Repositories;

import br.com.catolicapb.bd2projeto1.entity.Emprestimo;
import br.com.catolicapb.bd2projeto1.infrastructure.loaders.DataLoader;
import jakarta.persistence.EntityManager;

public class EmprestimoRepository {
    public void addEmprestimo(Emprestimo emprestimo) {
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();
        em.persist(emprestimo);
        em.getTransaction().commit();
        em.close();
    }
}
