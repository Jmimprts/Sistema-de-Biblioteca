package br.com.catolicapb.bd2projeto1.application;

import br.com.catolicapb.bd2projeto1.application.interfaces.ILeitorService;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.LeitorRepository;

public class LeitorService implements ILeitorService {
    private LeitorRepository _leitorRepository;

    public LeitorService(LeitorRepository leitorRepository) {
        _leitorRepository = leitorRepository;
    }

    @Override
    public void cadastrarleitor() {
        
    }
}
