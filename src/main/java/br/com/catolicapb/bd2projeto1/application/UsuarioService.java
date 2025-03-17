package br.com.catolicapb.bd2projeto1.application;

import br.com.catolicapb.bd2projeto1.application.interfaces.IUsuarioService;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.UsuarioRepository;

public class UsuarioService implements IUsuarioService {
    private UsuarioRepository _usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        _usuarioRepository = usuarioRepository;
    }

    @Override
    public void login(String nome, String senha) {

    }

    @Override
    public void logout() {

    }

    @Override
    public void cadastrar(String email, String nome, String senha) {

    }
}
