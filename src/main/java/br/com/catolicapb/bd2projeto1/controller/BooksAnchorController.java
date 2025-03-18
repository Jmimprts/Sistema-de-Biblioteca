package br.com.catolicapb.bd2projeto1.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class BooksAnchorController {

    @FXML
    private ChoiceBox<String> cbFilter;

    public void initialize() {
        cbFilter.getItems().addAll("Exibir todos", "Exibir apenas os disponíveis", "Exibir os mais reservados");
        cbFilter.setValue("Exibir todos");
    }
}
