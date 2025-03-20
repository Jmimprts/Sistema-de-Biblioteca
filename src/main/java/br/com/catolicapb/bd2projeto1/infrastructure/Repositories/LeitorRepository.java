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
    public void adicionarLeitor(Leitor leitor) {
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();
        em.persist(leitor);
        em.getTransaction().commit();
        em.close();
    }

    public void deletarLeitor(Long id) {
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();
        Leitor leitor = em.find(Leitor.class, id);
        if (leitor != null) {
            em.remove(leitor);
        }
        em.getTransaction().commit();
        em.close();
    }

    public int consultarTotalEmprestimoPorLeitor(Long idLeitor) {
        EntityManager em = DataLoader.getEntityManager();
        String jpql = "SELECT COUNT(e) FROM Emprestimo e WHERE e.leitor.id = :leitorId";
        Long total = em.createQuery(jpql, Long.class)
                .setParameter("leitorId", idLeitor)
                .getSingleResult();

        return total.intValue();
    }

    public List<Leitor> getLeitoresComMaisDeUmEmprestimoPendente() {
        EntityManager em = DataLoader.getEntityManager();
        TypedQuery<Leitor> query = em.createQuery(
                "SELECT l FROM Leitor l " +
                        "WHERE (SELECT COUNT(e) FROM Emprestimo e WHERE e.leitor = l AND e.status = :statusPendente) > 1",
                Leitor.class);
        query.setParameter("statusPendente", StatusEmprestimo.PENDENTE);
        List<Leitor> leitores = query.getResultList();
        em.close();
        return leitores;
    }

    public List<Leitor> buscarLeitoresSemEmprestimos() {
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
