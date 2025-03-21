package br.com.catolicapb.bd2projeto1.infrastructure.Repositories;

import br.com.catolicapb.bd2projeto1.entity.Usuario;
import br.com.catolicapb.bd2projeto1.infrastructure.loaders.DataLoader;
import jakarta.persistence.EntityManager;

import java.util.List;

public class UsuarioRepository {

    public void addUsuario(Usuario usuario) {
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
        em.close();
    }

    public List<Usuario> getAllUsuarios() {
        EntityManager em = DataLoader.getEntityManager();
        List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        em.close();
        return usuarios;
    }

    public Usuario verificacaoUserESenha(String email, String senha) {

        try (EntityManager em = DataLoader.getEntityManager()) {
            return em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha", Usuario.class)
                    .setParameter("email", email)
                    .setParameter("senha", senha)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
