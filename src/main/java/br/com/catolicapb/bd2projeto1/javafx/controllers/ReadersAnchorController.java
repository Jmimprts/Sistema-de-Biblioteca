package br.com.catolicapb.bd2projeto1.javafx.controllers;

import br.com.catolicapb.bd2projeto1.entity.Leitor;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.EmprestimoRepository;
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

import java.util.List;

public class ReadersAnchorController implements IOnChangeScreen {

    LeitorRepository leitorRepository = new LeitorRepository();
    EmprestimoRepository emprestimoRepository = new EmprestimoRepository();

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
    public void initialize() {
        ScreenManager.addOnChangeScreenListener(this);

        cbFilter.getItems().addAll("Exibir todos", "Exibir leitores com mais de um empréstimo pendente", "Exibir leitores que nunca fizeram empréstimos");
        cbFilter.setValue("Exibir todos");

        configureTableColumns();
        loadLeitoresByFilter();
        cbFilter.setOnAction(event -> loadLeitoresByFilter());
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
            loadLeitoresByFilter();
        }
    }

    private void configureTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        CPFColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        loansColumn.setCellValueFactory(cellData -> {
            Leitor leitor = cellData.getValue();
            String selectedFilter = cbFilter.getValue();
            int loanCount;
            if (selectedFilter.equals("Exibir leitores com mais de um empréstimo pendente")) {
                loanCount = emprestimoRepository.countPendingEmprestimosByLeitor(leitor);
            } else {
                loanCount = emprestimoRepository.countTotalEmprestimosByLeitor(leitor);
            }
            return new SimpleIntegerProperty(loanCount).asObject();
        });
    }

    private void loadLeitoresByFilter() {
        List<Leitor> leitores = null;
        String selectedFilter = cbFilter.getValue();

        if (selectedFilter.equals("Exibir todos")) {
            leitores = leitorRepository.getAllLeitores();
        } else if (selectedFilter.equals("Exibir leitores com mais de um empréstimo pendente")) {
            leitores = leitorRepository.getLeitoresComMaisDeUmEmprestimoPendente();
        } else if (selectedFilter.equals("Exibir leitores que nunca fizeram empréstimos")) {
            leitores = leitorRepository.getLeitoresSemEmprestimos();
        }

        if (leitores != null) {
            readersTv.setItems(FXCollections.observableArrayList(leitores));
        }
    }
}
