package br.com.catolicapb.bd2projeto1.infrastructure.Repositories;

import br.com.catolicapb.bd2projeto1.entity.Livro;
import br.com.catolicapb.bd2projeto1.entity.Reserva;
import br.com.catolicapb.bd2projeto1.enums.StatusReserva;
import br.com.catolicapb.bd2projeto1.infrastructure.loaders.DataLoader;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ReservaRepository {

    public void addReserva(Reserva reserva) {
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();
        em.persist(reserva);
        em.getTransaction().commit();
        em.close();
    }
    public List<Reserva> getAllReservas() {
        EntityManager em = DataLoader.getEntityManager();
        List<Reserva> reservas = em.createQuery("SELECT r FROM Reserva r", Reserva.class).getResultList();
        em.close();
        return reservas;
    }
    public void updateReserva(Reserva reserva) {
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();
        em.merge(reserva);
        em.getTransaction().commit();
        em.close();
    }
    public int countActiveReservationsByLivro(Livro livro) {
        EntityManager em = DataLoader.getEntityManager();
        try {
            Long countResult = em.createQuery("SELECT COUNT(r) FROM Reserva r WHERE r.livro = :livro AND r.status = :status", Long.class)
                    .setParameter("livro", livro)
                    .setParameter("status", StatusReserva.ATIVA)
                    .getSingleResult();
            return countResult.intValue();
        } finally {
            em.close();
        }
    }
}
