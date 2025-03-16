package br.com.catolicapb.bd2projeto1.controller;

import br.com.catolicapb.bd2projeto1.util.ScreenManager;
import javafx.fxml.FXML;

public class LoginScreenController {

    @FXML
    void btEnterAction() {
        loginProcedure();
    }

    private void loginProcedure() {
        ScreenManager.changeScene("mainScene");
    }
}
