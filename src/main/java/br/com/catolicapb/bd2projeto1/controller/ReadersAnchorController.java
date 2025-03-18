package br.com.catolicapb.bd2projeto1.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ReadersAnchorController {

    @FXML
    private ChoiceBox<String> cbFilter;

    public void initialize() {
        cbFilter.getItems().addAll("Exibir todos", "Exibir leitores com mais de um empréstimo pendente", "Exibir leitores que nunca fizeram empréstimos");
        cbFilter.setValue("Exibir todos");
    }
}
