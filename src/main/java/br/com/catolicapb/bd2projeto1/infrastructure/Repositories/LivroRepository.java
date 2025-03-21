package br.com.catolicapb.bd2projeto1.infrastructure.Repositories;

import br.com.catolicapb.bd2projeto1.entity.Livro;
import br.com.catolicapb.bd2projeto1.infrastructure.loaders.DataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class LivroRepository {

    public void addLivro(Livro livro) {
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();
        em.persist(livro);
        em.getTransaction().commit();
        em.close();
    }

    public void updateLivro(Livro livro) {
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();
        em.merge(livro);
        em.getTransaction().commit();
        em.close();
    }

    public void incrementQuantidadeLivroPorTitulo(String titulo) {
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();

        TypedQuery<Livro> query = em.createQuery("SELECT l FROM Livro l WHERE l.titulo = :titulo", Livro.class);
        query.setParameter("titulo", titulo);
        List<Livro> livros = query.getResultList();
        Livro livro = livros.getFirst();
        int quantidadeNova = livro.getQuantidadeDisponivel() + 1;
        livro.setQuantidadeDisponivel(quantidadeNova);

        em.merge(livro);
        em.getTransaction().commit();
        em.close();
    }

    public List<Livro> getAllLivros() {
        EntityManager em = DataLoader.getEntityManager();
        List<Livro> livros = em.createQuery("SELECT l FROM Livro l", Livro.class).getResultList();
        em.close();
        return livros;
    }

    public List<Livro> getLivrosDisponiveis() {
        EntityManager em = DataLoader.getEntityManager();
        TypedQuery<Livro> query = em.createQuery("SELECT l FROM Livro l WHERE l.quantidadeDisponivel > 0", Livro.class);
        List<Livro> livros = query.getResultList();
        em.close();
        return livros;
    }

    public List<Livro> getLivrosIndisponiveis() {
        EntityManager em = DataLoader.getEntityManager();
        TypedQuery<Livro> query = em.createQuery("SELECT l FROM Livro l WHERE l.quantidadeDisponivel = 0", Livro.class);
        List<Livro> livros = query.getResultList();
        em.close();
        return livros;
    }

    public List<Livro> getLivrosMaisReservados() {
        EntityManager em = DataLoader.getEntityManager();
        TypedQuery<Livro> query = em.createQuery(
                "SELECT l FROM Livro l " +
                        "JOIN l.reservas r " +
                        "GROUP BY l.id " +
                        "ORDER BY COUNT(r.id) DESC", Livro.class);

        query.setMaxResults(5); // Define o limite corretamente
        List<Livro> livros = query.getResultList();
        em.close();
        return livros;
    }
}
