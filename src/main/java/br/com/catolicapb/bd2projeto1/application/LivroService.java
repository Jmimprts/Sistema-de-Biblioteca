package br.com.catolicapb.bd2projeto1.application;

import br.com.catolicapb.bd2projeto1.application.interfaces.ILivroService;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.LivroRepository;

public class LivroService implements ILivroService {
    private LivroRepository _livroRepository;

    public LivroService(LivroRepository livroRepository) {
        _livroRepository = livroRepository;
    }
    
    @Override
    public void criarLivro() {
        
    }
}
