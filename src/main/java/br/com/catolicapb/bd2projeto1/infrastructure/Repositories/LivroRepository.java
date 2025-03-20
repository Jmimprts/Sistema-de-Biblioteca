package br.com.catolicapb.bd2projeto1.infrastructure.Repositories;

import br.com.catolicapb.bd2projeto1.entity.Emprestimo;
import br.com.catolicapb.bd2projeto1.entity.Leitor;
import br.com.catolicapb.bd2projeto1.entity.Livro;
import br.com.catolicapb.bd2projeto1.entity.Reserva;
import br.com.catolicapb.bd2projeto1.infrastructure.loaders.DataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.List;

public class LivroRepository {
    public void addLivro(Livro livro) {
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();
        em.persist(livro);
        em.getTransaction().commit();
        em.close();
    }

    public void removerLivro(Long id){
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();
        Livro livro = em.find(Livro.class, id);
        if (livro != null){
            em.remove(livro);
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<Livro> buscarLivroParaEmprestimo() {
        EntityManager em = DataLoader.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Livro> query = cb.createQuery(Livro.class);
        Root<Livro> root = query.from(Livro.class);
        query.select(root).where(cb.greaterThan(root.get("quantidadeDisponivel"), 0));
        // executa a consulta e retorna a lista
        return em.createQuery(query).getResultList();
    }


    //"Object[]" é utilizado quando é retornado multiplos campos (id, titulo e contagem)
    public List<Object[]> buscarLivrosMaisReservados() {
        EntityManager em = DataLoader.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);

        Root<Reserva> root = query.from(Reserva.class);
        Join<Reserva, Livro> livroJoin = root.join("livro");

        query.multiselect(
                livroJoin.get("id"),
                livroJoin.get("titulo"),
                cb.count(root)
        )
                .groupBy(livroJoin.get("id"), livroJoin.get("titulo"))
                .orderBy(cb.desc(cb.count(root)));
        // retorna a consulta e mostra os 5 mais reservados
        return em.createQuery(query).setMaxResults(5).getResultList();
    }

    public List<Leitor> consultarLeitoresSemEmprestimo() {
        EntityManager em = DataLoader.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Leitor> query = cb.createQuery(Leitor.class);

        Root<Leitor> leitorRoot = query.from(Leitor.class);

        Subquery<Long> subquery = query.subquery(Long.class);
        Root<Emprestimo> emprestimoRoot = subquery.from(Emprestimo.class);
        subquery.select(cb.literal(1L))
                .where(cb.equal(emprestimoRoot.get("leitor"), leitorRoot));

        query.select(leitorRoot)
                .where(cb.not(cb.exists(subquery)));

        return em.createQuery(query).getResultList();
    }

    public List<Object[]> listarLivrosEmprestadosComDevolucaoPendente() {
        EntityManager em = DataLoader.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);

        Root<Emprestimo> root = query.from(Emprestimo.class);
        Join<Emprestimo, Livro> livroJoin = root.join("livro");

        query.multiselect(
                livroJoin.get("id"),
                livroJoin.get("titulo"),
                root.get("dataDevolucao")
        ).where(cb.isNull(root.get("dataDevolucao")));

        return em.createQuery(query).getResultList();
    }
}
