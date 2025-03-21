package br.com.catolicapb.bd2projeto1.javafx.controllers;

import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.LeitorRepository;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.UsuarioRepository;
import br.com.catolicapb.bd2projeto1.javafx.interfaces.IOnChangeScreen;
import br.com.catolicapb.bd2projeto1.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeAnchorController implements IOnChangeScreen {

    //SOMENTE TESTES, ALTERAR
    UsuarioRepository usuarioRepository = new UsuarioRepository();
    LeitorRepository leitorRepository = new LeitorRepository();

    @FXML
    private Label lblUsersCount;
    @FXML
    private Label lblLoansCount;

    @FXML
    public void initialize() {
        ScreenManager.addOnChangeScreenListener(this);

        loadLabelsCountUser();
        loadLabelsCountLoans();
    }

    public void loadLabelsCountUser() {
        int usersCount = usuarioRepository.getAllUsuarios().size();
        lblUsersCount.setText(String.valueOf(usersCount));
    }

    public void loadLabelsCountLoans(){
        int loansCount = leitorRepository.getAllLeitores().size();
        lblLoansCount.setText(String.valueOf(loansCount));
    }

    @Override
    public void onScreenChanged(String newScreen) {
        if (newScreen.equals("homeAnchor")) {
            loadLabelsCountUser();
            System.out.println("AnchorPane da home carregada!");
        }
    }
}
