package br.com.catolicapb.bd2projeto1.infrastructure.Repositories;

import br.com.catolicapb.bd2projeto1.entity.Emprestimo;
import br.com.catolicapb.bd2projeto1.entity.Leitor;
import br.com.catolicapb.bd2projeto1.enums.StatusEmprestimo;
import br.com.catolicapb.bd2projeto1.infrastructure.loaders.DataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class EmprestimoRepository {
    public void addEmprestimo(Emprestimo emprestimo) {
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();
        em.persist(emprestimo);
        em.getTransaction().commit();
        em.close();
    }
    public List<Emprestimo> getAllEmprestimos() {
        EntityManager em = DataLoader.getEntityManager();
        List<Emprestimo> emprestimos = em.createQuery("SELECT e FROM Emprestimo e", Emprestimo.class).getResultList();
        em.close();
        return emprestimos;
    }

    public List<Emprestimo> getEmprestimosPendentes() {
        EntityManager em = DataLoader.getEntityManager();
        TypedQuery<Emprestimo> query = em.createQuery("SELECT e FROM Emprestimo e WHERE e.status = :status", Emprestimo.class);
        query.setParameter("status", StatusEmprestimo.PENDENTE);
        List<Emprestimo> emprestimos = query.getResultList();
        em.close();
        return emprestimos;
    }

    public List<Emprestimo> getEmprestimosAtrasados() {
        EntityManager em = DataLoader.getEntityManager();
        TypedQuery<Emprestimo> query = em.createQuery("SELECT e FROM Emprestimo e WHERE e.status = :status", Emprestimo.class);
        query.setParameter("status", StatusEmprestimo.ATRASADO);
        List<Emprestimo> emprestimos = query.getResultList();
        em.close();
        return emprestimos;
    }

    public void updateEmprestimo(Emprestimo emprestimo) {
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();
        em.merge(emprestimo);
        em.getTransaction().commit();
        em.close();
    }
    public int countTotalEmprestimosByLeitor(Leitor leitor) {
        EntityManager em = DataLoader.getEntityManager();
        try {
            Long countResult = em.createQuery("SELECT COUNT(e) FROM Emprestimo e WHERE e.leitor = :leitor", Long.class)
                    .setParameter("leitor", leitor)
                    .getSingleResult();
            return countResult.intValue();
        } finally {
            em.close();
        }
    }

    public int countPendingEmprestimosByLeitor(Leitor leitor) {
        EntityManager em = DataLoader.getEntityManager();
        try {
            Long countResult = em.createQuery("SELECT COUNT(e) FROM Emprestimo e WHERE e.leitor = :leitor AND (e.status = :statusPendente OR e.status = :statusAtrasado)", Long.class)
                    .setParameter("leitor", leitor)
                    .setParameter("statusPendente", StatusEmprestimo.PENDENTE)
                    .setParameter("statusAtrasado", StatusEmprestimo.ATRASADO)
                    .getSingleResult();
            return countResult.intValue();
        } finally {
            em.close();
        }
    }
}