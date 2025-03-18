package br.com.catolicapb.bd2projeto1.javafx.controllers;

import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.UsuarioRepository;
import br.com.catolicapb.bd2projeto1.javafx.interfaces.IOnChangeScreen;
import br.com.catolicapb.bd2projeto1.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeAnchorController implements IOnChangeScreen {

    //SOMENTE TESTES, ALTERAR
    UsuarioRepository usuarioRepository = new UsuarioRepository();

    @FXML
    private Label lblUsersCount;

    @FXML
    public void initialize() {
        ScreenManager.addOnChangeScreenListener(this);

        loadLabelsCount();
    }

    public void loadLabelsCount() {
        int usersCount = usuarioRepository.getAllUsuarios().size();
        lblUsersCount.setText(String.valueOf(usersCount));
    }

    @Override
    public void onScreenChanged(String newScreen) {
        if (newScreen.equals("homeAnchor")) {
            loadLabelsCount();
            System.out.println("AnchorPane da home carregada!");
        }
    }
}
