package br.com.catolicapb.bd2projeto1.javafx.controllers;

import br.com.catolicapb.bd2projeto1.entity.Leitor;
import br.com.catolicapb.bd2projeto1.entity.Livro;
import br.com.catolicapb.bd2projeto1.entity.Reserva;
import br.com.catolicapb.bd2projeto1.javafx.interfaces.IOnChangeScreen;
import br.com.catolicapb.bd2projeto1.util.ScreenManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class ReservationAnchorController implements IOnChangeScreen {

    @FXML
    private TableColumn<Reserva, String> bookColumn;
    @FXML
    private ChoiceBox<Livro> cbBook;
    @FXML
    private ChoiceBox<String> cbFilter;
    @FXML
    private ChoiceBox<Leitor> cbReader;
    @FXML
    private DatePicker dpReservationDate;
    @FXML
    private TableView<Reserva> loansTv;
    @FXML
    private TableColumn<Reserva, String> readerColumn;
    @FXML
    private TableColumn<Reserva, LocalDate> reservationDateColumn;
    @FXML
    private TextField searchTf;
    @FXML
    private TableColumn<Reserva, String> statusColumn;

    @FXML
    public void initialize() {
        ScreenManager.addOnChangeScreenListener(this);

        cbFilter.getItems().addAll("Exibir todas", "Somente ativas", "Somente concluídas");
        cbFilter.setValue("Exibir todas");

        configureTableColumns();
        loadAllReservations();
    }

    @FXML
    void addBtnAction() {

    }

    @Override
    public void onScreenChanged(String newScreen) {
        if (newScreen.equals("reservationsAnchor")) {
            loadAllReservations();
        }
    }

    private void configureTableColumns() {
        reservationDateColumn.setCellValueFactory(new PropertyValueFactory<>("dataReserva"));
        statusColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus().toString()));
        bookColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLivro().getTitulo()));
        readerColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLeitor().getNome()));
    }

    private void loadAllReservations() {
        //List<Reserva> reservas = reservaRepository.getAllReservas();
        //reservationsTv.setItems(FXCollections.observableArrayList(reservas));
    }

}
