package br.com.catolicapb.bd2projeto1.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class LoansAnchorController {

    @FXML
    private ChoiceBox<String> cbFilter;

    public void initialize() {
        cbFilter.getItems().addAll("Exibir todos", "Exibir empréstimos pendentes", "Exibir empréstimos atrasados");
        cbFilter.setValue("Exibir todos");
    }
}
