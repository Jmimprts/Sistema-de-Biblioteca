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
}
