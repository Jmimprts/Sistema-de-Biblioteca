package br.com.catolicapb.bd2projeto1.javafx.controllers;

import br.com.catolicapb.bd2projeto1.entity.Leitor;
import br.com.catolicapb.bd2projeto1.entity.Livro;
import br.com.catolicapb.bd2projeto1.entity.Reserva;
import br.com.catolicapb.bd2projeto1.enums.StatusReserva;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.LeitorRepository;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.LivroRepository;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.ReservaRepository;
import br.com.catolicapb.bd2projeto1.javafx.interfaces.IOnChangeScreen;
import br.com.catolicapb.bd2projeto1.util.AlertHelper;
import br.com.catolicapb.bd2projeto1.util.ScreenManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReservationAnchorController implements IOnChangeScreen {

    ReservaRepository reservaRepository = new ReservaRepository();
    LeitorRepository leitorRepository = new LeitorRepository();
    LivroRepository livroRepository = new LivroRepository();

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
    private TableView<Reserva> reservationsTv;
    @FXML
    private TableColumn<Reserva, String> readerColumn;
    @FXML
    private TableColumn<Reserva, LocalDate> reservationDateColumn;
    @FXML
    private TextField searchTf;
    @FXML
    private TableColumn<Reserva, StatusReserva> statusColumn;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
        LocalDate dataReserva = dpReservationDate.getValue();
        Livro livro = cbBook.getValue();
        Leitor leitor = cbReader.getValue();

        if(!validateFields()){
            return;
        }

        Reserva reserva = new Reserva();
        reserva.setDataReserva(dataReserva);
        reserva.setLivro(livro);
        reserva.setLeitor(leitor);
        reservaRepository.addReserva(reserva);

        loadAllReservations();
        loadUnavailableBooksComboBox();
        loadReadersComboBox();
        clearInputFields();
        AlertHelper.showAlert("Reserva realizada com sucesso!", "INFO");
    }

    @Override
    public void onScreenChanged(String newScreen) {
        if (newScreen.equals("reservationsAnchor")) {
            loadAllReservations();
            loadUnavailableBooksComboBox();
            loadReadersComboBox();
        }
    }

    private boolean validateFields() {
        StringBuilder errorMessage = new StringBuilder();

        if (cbReader.getValue() == null) {
            errorMessage.append("⚠ O campo Leitor deve ser preenchido.\n");
        }

        if (cbBook.getValue() == null) {
            errorMessage.append("⚠ O campo livro deve ser preenchido.\n");
        }

        if (dpReservationDate.getValue() == null) {
            errorMessage.append("⚠ O campo data de reserva deve ser preenchido.\n");
        }

        if (!errorMessage.isEmpty()) {
            AlertHelper.showAlert(errorMessage.toString().trim(), "ERROR");
            return false;
        }

        return true;
    }

    private void configureTableColumns() {
        reservationDateColumn.setCellValueFactory(new PropertyValueFactory<>("dataReserva"));
        bookColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLivro().getTitulo()));
        readerColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLeitor().getNome()));

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new TableCell<Reserva, StatusReserva>() {
            private ComboBox<StatusReserva> statusComboBox;
            private Reserva currentReserva;

            @Override
            protected void updateItem(StatusReserva status, boolean empty) {
                super.updateItem(status, empty);

                if (empty || status == null) {
                    setText(null);
                    setGraphic(null);
                    if (statusComboBox != null) {
                        statusComboBox.setOnAction(null);
                    }
                } else {
                    currentReserva = getTableRow().getItem();
                    if (currentReserva != null) {
                        if (statusComboBox == null) {
                            statusComboBox = new ComboBox<>();
                        }
                        statusComboBox.getItems().setAll(currentReserva.getProximosStatusValidos());
                        statusComboBox.setValue(status);

                        if (status == StatusReserva.CANCELADA || status == StatusReserva.CONCLUIDA) {
                            statusComboBox.setDisable(true);
                        } else {
                            statusComboBox.setDisable(false);
                            statusComboBox.setOnAction(event -> {
                                StatusReserva novoStatus = statusComboBox.getValue();
                                if (novoStatus != null && novoStatus != currentReserva.getStatus()) {
                                    currentReserva.setStatus(novoStatus);
                                    reservaRepository.updateReserva(currentReserva);
                                    reservationsTv.refresh();
                                }
                            });
                        }
                        setGraphic(statusComboBox);
                    } else {
                        setText(status.toString());
                        setGraphic(null);
                    }
                }
            }
        });

        reservationDateColumn.setCellFactory(dateCellFactory());
    }

    private Callback<TableColumn<Reserva, LocalDate>, TableCell<Reserva, LocalDate>> dateCellFactory() {
        return column -> new TableCell<Reserva, LocalDate>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(dateFormatter.format(date));
                }
            }
        };
    }

    private void loadAllReservations() {
        List<Reserva> reservas = reservaRepository.getAllReservas();
        reservationsTv.setItems(FXCollections.observableArrayList(reservas));
    }

    private void loadReadersComboBox() {
        List<Leitor> leitores = leitorRepository.getAllLeitores();
        cbReader.setItems(FXCollections.observableArrayList(leitores));
    }

    private void loadUnavailableBooksComboBox() {
        List<Livro> livrosIndisponiveis = livroRepository.getLivrosIndisponiveis();
        cbBook.setItems(FXCollections.observableArrayList(livrosIndisponiveis));
    }


    private void clearInputFields() {
        cbReader.setValue(null);
        cbBook.setValue(null);
        dpReservationDate.setValue(null);
    }
}
