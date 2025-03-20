package br.com.catolicapb.bd2projeto1.infrastructure.Repositories;

import br.com.catolicapb.bd2projeto1.entity.Emprestimo;
import br.com.catolicapb.bd2projeto1.entity.Leitor;
import br.com.catolicapb.bd2projeto1.infrastructure.loaders.DataLoader;
import jakarta.persistence.EntityManager;
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

    public int consultarTotalEmprestimoPorLeitor(Long idLeitor) {
        EntityManager em = DataLoader.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<Emprestimo> root = query.from(Emprestimo.class);
        query.select(cb.count(root))
                .where(cb.equal(root.get("leitor").get("id"), idLeitor));

        Long resultado = em.createQuery(query).getSingleResult();
        return resultado != null ? resultado.intValue() : 0;
    }

    public List<Leitor> buscarLeitoresComEmprestimosPendente() {
        EntityManager em = DataLoader.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Leitor> query = cb.createQuery(Leitor.class);

        Root<Emprestimo> root = query.from(Emprestimo.class);
        Join<Emprestimo, Leitor> leitorJoin = root.join("leitor");

        query.select(leitorJoin)
                .groupBy(leitorJoin.get("id"), leitorJoin.get("nome"))
                .having(cb.greaterThan(cb.count(root), cb.literal(1L)))
                .where(cb.equal(root.get("status"), "PENDENTE"));

        return em.createQuery(query).getResultList();
    }
}
