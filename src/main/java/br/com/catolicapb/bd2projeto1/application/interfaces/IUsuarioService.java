package br.com.catolicapb.bd2projeto1.application.interfaces;

public interface IUsuarioService {
    public void login(String nome, String senha);
    public void logout();
    public void cadastrar(String email, String nome, String senha);
}
