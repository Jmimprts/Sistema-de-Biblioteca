package br.com.catolicapb.bd2projeto1.javafx.controllers;

import br.com.catolicapb.bd2projeto1.entity.Emprestimo;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.EmprestimoRepository;
import br.com.catolicapb.bd2projeto1.javafx.interfaces.IOnChangeScreen;
import br.com.catolicapb.bd2projeto1.util.ScreenManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class LoansAnchorController implements IOnChangeScreen {

    EmprestimoRepository emprestimoRepository = new EmprestimoRepository();

    @FXML
    private TableColumn<Emprestimo, String> bookColumn;
    @FXML
    private ChoiceBox<String> cbFilter;
    @FXML
    private TableColumn<Emprestimo, LocalDate> loanDateColumn;
    @FXML
    private TableColumn<Emprestimo, String> readerColumn;
    @FXML
    private TableColumn<Emprestimo, LocalDate> returnDateColumn;
    @FXML
    private TableView<Emprestimo> loansTv;

    @FXML
    public void initialize() {
        ScreenManager.addOnChangeScreenListener(this);

        cbFilter.getItems().addAll("Exibir todos", "Exibir empréstimos pendentes", "Exibir empréstimos atrasados");
        cbFilter.setValue("Exibir todos");

        configureTableColumns();
        loadAllEmprestimos();
    }

    @Override
    public void onScreenChanged(String newScreen) {
        if (newScreen.equals("loansAnchor")) {
            loadAllEmprestimos();
        }
    }

    private void configureTableColumns() {
        bookColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        loanDateColumn.setCellValueFactory(new PropertyValueFactory<>("dataEmprestimo"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("dataDevolucao"));
        readerColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLeitor().getNome())
        );
        bookColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLivro().getTitulo())
        );
    }

    private void loadAllEmprestimos() {
        //List<Emprestimo> emprestimos = emprestimoRepository.getAllEmprestimos();
        //loansTv.setItems(FXCollections.observableArrayList(emprestimos));
    }
}
