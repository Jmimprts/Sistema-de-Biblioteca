package br.com.catolicapb.bd2projeto1.infrastructure.Repositories;

import br.com.catolicapb.bd2projeto1.entity.Emprestimo;
import br.com.catolicapb.bd2projeto1.entity.Leitor;
import br.com.catolicapb.bd2projeto1.enums.StatusEmprestimo;
import br.com.catolicapb.bd2projeto1.infrastructure.loaders.DataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class LeitorRepository {
    public void addLeitor(Leitor leitor) {
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();
        em.persist(leitor);
        em.getTransaction().commit();
        em.close();
    }

    public List<Leitor> getAllLeitores() {
        EntityManager em = DataLoader.getEntityManager();
        List<Leitor> leitores = em.createQuery("SELECT l FROM Leitor l", Leitor.class).getResultList();
        em.close();
        return leitores;
    }

    public Leitor getLeitorById(Long id) {
        EntityManager em = DataLoader.getEntityManager();
        Leitor leitor = em.find(Leitor.class, id);
        em.close();
        return leitor;
    }

    public void updateLeitor(Leitor leitor) {
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();
        em.merge(leitor);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteLeitor(Long id) {
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();
        Leitor leitor = em.find(Leitor.class, id);
        if (leitor != null) {
            em.remove(leitor);
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<Leitor> getLeitoresComMaisDeUmEmprestimoPendente() {
        EntityManager em = DataLoader.getEntityManager();
        TypedQuery<Leitor> query = em.createQuery(
                "SELECT l FROM Leitor l " +
                        "WHERE (SELECT COUNT(e) FROM Emprestimo e WHERE e.leitor = l AND (e.status = :statusPendente OR e.status = :statusAtrasado)) > 1",
                Leitor.class);
        query.setParameter("statusPendente", StatusEmprestimo.PENDENTE);
        query.setParameter("statusAtrasado", StatusEmprestimo.ATRASADO);
        List<Leitor> leitores = query.getResultList();
        em.close();
        return leitores;
    }

    public List<Leitor> getLeitoresSemEmprestimos() {
        EntityManager em = DataLoader.getEntityManager();
        TypedQuery<Leitor> query = em.createQuery(
                "SELECT l FROM Leitor l " +
                        "WHERE NOT EXISTS (SELECT e FROM Emprestimo e WHERE e.leitor = l)",
                Leitor.class);
        List<Leitor> leitores = query.getResultList();
        em.close();
        return leitores;
    }

}
