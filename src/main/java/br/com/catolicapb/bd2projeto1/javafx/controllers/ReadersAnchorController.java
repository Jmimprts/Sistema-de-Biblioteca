package br.com.catolicapb.bd2projeto1.javafx.controllers;

import br.com.catolicapb.bd2projeto1.javafx.interfaces.IOnChangeScreen;
import br.com.catolicapb.bd2projeto1.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ReadersAnchorController implements IOnChangeScreen {

    @FXML
    private ChoiceBox<String> cbFilter;

    public void initialize() {
        cbFilter.getItems().addAll("Exibir todos", "Exibir leitores com mais de um empréstimo pendente", "Exibir leitores que nunca fizeram empréstimos");
        cbFilter.setValue("Exibir todos");

        ScreenManager.addOnChangeScreenListener(this);
    }

    @Override
    public void onScreenChanged(String newScreen) {
        if (newScreen.equals("readersAnchor")) {
            System.out.println("AnchorPane dos leitores carregada!");
        }
    }
}
