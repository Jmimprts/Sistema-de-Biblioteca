package br.com.catolicapb.bd2projeto1.javafx.controllers;

import br.com.catolicapb.bd2projeto1.entity.Leitor;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.LeitorRepository;
import br.com.catolicapb.bd2projeto1.javafx.interfaces.IOnChangeScreen;
import br.com.catolicapb.bd2projeto1.util.AlertHelper;
import br.com.catolicapb.bd2projeto1.util.ScreenManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.Text;

import java.util.List;

public class ReadersAnchorController implements IOnChangeScreen {

    LeitorRepository leitorRepository = new LeitorRepository();

    @FXML
    private TextField nameTf;
    @FXML
    private TextField CPFTf;
    @FXML
    private ChoiceBox<String> cbFilter;
    @FXML
    private TableColumn<Leitor, Integer> loansColumn;
    @FXML
    private TableColumn<Leitor, Integer> CPFColumn;
    @FXML
    private TableColumn<Leitor, Integer> nameColumn;
    @FXML
    private TableView<Leitor> readersTv;
    @FXML
    private TableColumn<Leitor, Integer> reservesColumn;

    @FXML
    public void initialize() {
        ScreenManager.addOnChangeScreenListener(this);

        cbFilter.getItems().addAll("Exibir todos", "Exibir leitores com mais de um empréstimo pendente", "Exibir leitores que nunca fizeram empréstimos");
        cbFilter.setValue("Exibir todos");

        configureTableColumns();
        loadAllLeitores();
    }

    @FXML
    void addBtnAction() {
        String name = nameTf.getText().trim();
        String cpf = CPFTf.getText().trim();

        Leitor leitor = new Leitor();
        leitor.setCpf(cpf);
        leitor.setNome(name);
        leitorRepository.addLeitor(leitor);

        AlertHelper.showAlert("Leitor adicionado com sucesso", "INFO");
    }

    @Override
    public void onScreenChanged(String newScreen) {
        if (newScreen.equals("readersAnchor")) {
            loadAllLeitores();
        }
    }

    private void configureTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        CPFColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        loansColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getEmprestimos().size()).asObject()
        );
        reservesColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getReservas().size()).asObject()
        );
    }

    private void loadAllLeitores() {
        //List<Leitor> leitores = leitorRepository.getAllLeitores();
        //readersTv.setItems(FXCollections.observableArrayList(leitores));
    }
}
