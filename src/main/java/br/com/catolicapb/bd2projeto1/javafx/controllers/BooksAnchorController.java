package br.com.catolicapb.bd2projeto1.javafx.controllers;

import br.com.catolicapb.bd2projeto1.javafx.interfaces.IOnChangeScreen;
import br.com.catolicapb.bd2projeto1.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class BooksAnchorController implements IOnChangeScreen {

    @FXML
    private ChoiceBox<String> cbFilter;

    public void initialize() {
        ScreenManager.addOnChangeScreenListener(this);

        cbFilter.getItems().addAll("Exibir todos", "Exibir apenas os disponíveis", "Exibir os mais reservados");
        cbFilter.setValue("Exibir todos");
    }

    @Override
    public void onScreenChanged(String newScreen) {
        if (newScreen.equals("booksAnchor")) {
            System.out.println("AnchorPane de livros carregada!");
        }
    }
}
