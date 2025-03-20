package br.com.catolicapb.bd2projeto1.javafx.controllers;

import br.com.catolicapb.bd2projeto1.entity.Usuario;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.UsuarioRepository;
import br.com.catolicapb.bd2projeto1.util.AlertHelper;
import br.com.catolicapb.bd2projeto1.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginScreenController {

    @FXML
    private TextField emailTf;

    @FXML
    private TextField pfPassword;

    private final UsuarioRepository usuarioRepository = new UsuarioRepository();

    @FXML
    void btEnterAction() {
        loginProcedure();
    }

    private void loginProcedure() {
        String email = emailTf.getText();
        String password = pfPassword.getText();

        Usuario usuario = usuarioRepository.verificacaoUserESenha(email, password);

        if (usuario != null){
            ScreenManager.changeScene("mainScene");
        } else {
            AlertHelper.showAlert("Usuário não existe!", "ERROR");
        }
    }
}
