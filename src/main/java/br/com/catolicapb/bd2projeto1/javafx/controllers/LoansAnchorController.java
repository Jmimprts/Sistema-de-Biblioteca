package br.com.catolicapb.bd2projeto1.javafx.controllers;

import br.com.catolicapb.bd2projeto1.entity.Emprestimo;
import br.com.catolicapb.bd2projeto1.entity.Leitor;
import br.com.catolicapb.bd2projeto1.entity.Livro;
import br.com.catolicapb.bd2projeto1.enums.StatusEmprestimo;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.EmprestimoRepository;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.LeitorRepository;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.LivroRepository;
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

public class LoansAnchorController implements IOnChangeScreen {

    EmprestimoRepository emprestimoRepository = new EmprestimoRepository();
    LeitorRepository leitorRepository = new LeitorRepository();
    LivroRepository livroRepository = new LivroRepository();

    @FXML
    private TableColumn<Emprestimo, String> bookColumn;
    @FXML
    private ChoiceBox<Livro> cbBook;
    @FXML
    private ChoiceBox<String> cbFilter;
    @FXML
    private ChoiceBox<Leitor> cbReader;
    @FXML
    private DatePicker dpReturnDate;
    @FXML
    private TableColumn<Emprestimo, LocalDate> loanDateColumn;
    @FXML
    private TableColumn<Emprestimo, String> readerColumn;
    @FXML
    private TableColumn<Emprestimo, LocalDate> returnDateColumn;
    @FXML
    private TableColumn<Emprestimo, StatusEmprestimo> statusColumn;
    @FXML
    private TableView<Emprestimo> loansTv;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        ScreenManager.addOnChangeScreenListener(this);

        cbFilter.getItems().addAll("Exibir todos", "Exibir empréstimos pendentes", "Exibir empréstimos atrasados");
        cbFilter.setValue("Exibir todos");

        configureTableColumns();
        loadEmprestimosByFilter();
        cbFilter.setOnAction(event -> loadEmprestimosByFilter());

        loadReadersComboBox();
        loadAvailableBooksComboBox();
    }

    @FXML
    void addBtnAction() {
        LocalDate dataEmprestimo = dpReturnDate.getValue();
        Livro livro = cbBook.getValue();
        Leitor leitor = cbReader.getValue();

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setDataEmprestimo(dataEmprestimo);
        emprestimo.setLivro(livro);
        emprestimo.setLeitor(leitor);
        emprestimoRepository.addEmprestimo(emprestimo);

        AlertHelper.showAlert("empréstimo realizado com sucesso!", "INFO");
    }

    @Override
    public void onScreenChanged(String newScreen) {
        if (newScreen.equals("loansAnchor")) {
            loadEmprestimosByFilter();
            loadReadersComboBox();
            loadAvailableBooksComboBox();
        }
    }

    private void configureTableColumns() {
        loanDateColumn.setCellValueFactory(new PropertyValueFactory<>("dataEmprestimo"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("dataDevolucao"));
        readerColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLeitor().getNome())
        );
        bookColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLivro().getTitulo())
        );
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new TableCell<Emprestimo, StatusEmprestimo>() {
            private ComboBox<StatusEmprestimo> statusComboBox;
            private Emprestimo currentEmprestimo;

            @Override
            protected void updateItem(StatusEmprestimo status, boolean empty) {
                super.updateItem(status, empty);

                if (empty || status == null) {
                    setText(null);
                    setGraphic(null);
                    if (statusComboBox != null) {
                        statusComboBox.setOnAction(null);
                    }
                } else {
                    currentEmprestimo = getTableRow().getItem();
                    if (currentEmprestimo != null) {
                        if (statusComboBox == null) {
                            statusComboBox = new ComboBox<>();
                        }
                        statusComboBox.getItems().setAll(currentEmprestimo.getProximosStatusValidos());
                        statusComboBox.setValue(status);

                        if (currentEmprestimo.getStatus() == StatusEmprestimo.FINALIZADO) {
                            statusComboBox.setDisable(true);
                        } else {
                            statusComboBox.setDisable(false);
                            statusComboBox.setOnAction(event -> {
                                StatusEmprestimo novoStatus = statusComboBox.getValue();
                                if (novoStatus != null && novoStatus != currentEmprestimo.getStatus()) {
                                    currentEmprestimo.setStatus(novoStatus);
                                    emprestimoRepository.updateEmprestimo(currentEmprestimo);
                                    loansTv.refresh();
                                    loadEmprestimosByFilter();
                                }
                            });
                        }
                        setGraphic(statusComboBox);
                    } else {
                        setText(status != null ? status.toString() : "");
                        setGraphic(null);
                    }
                }
            }
        });

        loanDateColumn.setCellFactory(dateCellFactory());
        returnDateColumn.setCellFactory(dateCellFactory());
    }

    private Callback<TableColumn<Emprestimo, LocalDate>, TableCell<Emprestimo, LocalDate>> dateCellFactory() {
        return column -> new TableCell<Emprestimo, LocalDate>() {
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

    private void loadEmprestimosByFilter() {
        List<Emprestimo> emprestimos = null;
        String selectedFilter = cbFilter.getValue();
        LocalDate today = LocalDate.now();

        if (selectedFilter.equals("Exibir todos")) {
            emprestimos = emprestimoRepository.getAllEmprestimos();
        } else if (selectedFilter.equals("Exibir empréstimos pendentes")) {
            emprestimos = emprestimoRepository.getEmprestimosPendentes();
        } else if (selectedFilter.equals("Exibir empréstimos atrasados")) {
            emprestimos = emprestimoRepository.getEmprestimosAtrasados();
        }

        if (emprestimos != null) {
            for (Emprestimo emprestimo : emprestimos) {
                if (emprestimo.getStatus() == StatusEmprestimo.PENDENTE && today.isAfter(emprestimo.getDataDevolucao())) {
                    emprestimo.setStatus(StatusEmprestimo.ATRASADO);
                    emprestimoRepository.updateEmprestimo(emprestimo);
                }
            }
            loansTv.setItems(FXCollections.observableArrayList(emprestimos));
        }
    }

    private void loadReadersComboBox() {
        List<Leitor> leitores = leitorRepository.getAllLeitores();
        cbReader.setItems(FXCollections.observableArrayList(leitores));
    }

    private void loadAvailableBooksComboBox() {
        List<Livro> livrosDisponiveis = livroRepository.getLivrosDisponiveis();
        cbBook.setItems(FXCollections.observableArrayList(livrosDisponiveis));
    }
}
