package br.com.catolicapb.bd2projeto1.infrastructure.Repositories;

import br.com.catolicapb.bd2projeto1.entity.Reserva;
import br.com.catolicapb.bd2projeto1.infrastructure.loaders.DataLoader;
import jakarta.persistence.EntityManager;

public class ReservaRepository {
    public void addReserva(Reserva reserva) {
        EntityManager em = DataLoader.getEntityManager();
        em.getTransaction().begin();
        em.persist(reserva);
        em.getTransaction().commit();
        em.close();
    }
}
