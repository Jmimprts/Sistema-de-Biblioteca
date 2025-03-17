package br.com.catolicapb.bd2projeto1.application;

import br.com.catolicapb.bd2projeto1.application.interfaces.IReservaService;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.ReservaRepository;

public class ReservaService implements IReservaService {
    private ReservaRepository _reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        _reservaRepository = reservaRepository;
    }
    
    @Override
    public void fazerReserva() {
        
    }
}
