package br.com.catolicapb.bd2projeto1.application.services;

import br.com.catolicapb.bd2projeto1.application.interfaces.IEmprestimoService;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.EmprestimoRepository;

public class EmprestimoService implements IEmprestimoService {
    private EmprestimoRepository _emprestimoRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        _emprestimoRepository = emprestimoRepository;
    }
    
    @Override
    public void realizarEmprestimo() {
        
    }
}
