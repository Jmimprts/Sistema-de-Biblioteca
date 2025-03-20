package br.com.catolicapb.bd2projeto1.javafx.controllers;

import br.com.catolicapb.bd2projeto1.entity.Livro;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.LivroRepository;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.ReservaRepository;
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

public class BooksAnchorController implements IOnChangeScreen {

    LivroRepository livroRepository = new LivroRepository();
    ReservaRepository reservaRepository = new ReservaRepository();

    @FXML
    private ChoiceBox<String> cbFilter;
    @FXML
    private TextField quantityTf;
    @FXML
    private TextField titleTf;
    @FXML
    private TextField yearTf;
    @FXML
    private TextField authorTf;
    @FXML
    private TableColumn<Livro, String> authorColumn;
    @FXML
    private TableColumn<Livro, Integer> quantityColumn;
    @FXML
    private TableColumn<Livro, String> titleColumn;
    @FXML
    private TableColumn<Livro, Integer> yearColumn;
    @FXML
    private TableColumn<Livro, Integer> reservationsColumn;
    @FXML
    private TableView<Livro> booksTv;

    @FXML
    void addBtnAction() {
        String quantity = quantityTf.getText().trim();
        String title = titleTf.getText().trim();
        String year = yearTf.getText().trim();
        String author = authorTf.getText().trim();

        if (!validateFields(author, year, title, quantity)){
            return;
        }

        Livro livro = new Livro();
        livro.setQuantidadeDisponivel(Integer.parseInt(quantity));
        livro.setTitulo(title);
        livro.setAnoPublicacao(Integer.parseInt(year));
        livro.setAutor(author);
        livroRepository.addLivro(livro);

        clearFields();
        AlertHelper.showAlert("Livro adicionado com sucesso", "INFO");
    }

    @FXML
    public void initialize() {
        ScreenManager.addOnChangeScreenListener(this);

        cbFilter.getItems().addAll("Exibir todos", "Exibir apenas os disponíveis", "Exibir os mais reservados");
        cbFilter.setValue("Exibir todos");

        configureTableColumns();
        loadLivrosByFilter();
        cbFilter.setOnAction(event -> loadLivrosByFilter());
    }

    @Override
    public void onScreenChanged(String newScreen) {
        if (newScreen.equals("booksAnchor")) {
            loadLivrosByFilter();
        }
    }

    private void configureTableColumns() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("autor"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("anoPublicacao"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantidadeDisponivel"));
        reservationsColumn.setCellValueFactory(cellData -> {
            Livro livro = cellData.getValue();
            int activeReservations = reservaRepository.countActiveReservationsByLivro(livro);
            return new SimpleIntegerProperty(activeReservations).asObject();
        });
    }

    private void loadLivrosByFilter() {
        List<Livro> livros = null;
        String selectedFilter = cbFilter.getValue();

        if (selectedFilter.equals("Exibir todos")) {
            livros = livroRepository.getAllLivros();
        } else if (selectedFilter.equals("Exibir apenas os disponíveis")) {
            livros = livroRepository.getLivrosDisponiveis();
        } else if (selectedFilter.equals("Exibir os mais reservados")) {
            // Lógica para exibir os mais reservados será adicionada depois
            livros = livroRepository.getAllLivros(); // Por enquanto, exibe todos
        }

        if (livros != null) {
            booksTv.setItems(FXCollections.observableArrayList(livros));
        }
    }

    private void clearFields() {
        quantityTf.clear();
        titleTf.clear();
        yearTf.clear();
        authorTf.clear();
    }

    private boolean validateFields(String author, String year, String title, String quantity) {
        StringBuilder errorMessage = new StringBuilder();

        if (author.isEmpty()) {
            errorMessage.append("⚠ O campo Autor deve ser preenchido.\n");
        }

        if (year.isEmpty()) {
            errorMessage.append("⚠ O campo Email deve ser preenchido.\n");
        } else if (year.matches("^[0-9]+$")) {
            errorMessage.append("⚠ O Campo só pode aceitar caracteres numéricos.\n");
        }else if (year.length() != 4) {
            errorMessage.append("⚠ Número deve ter 4 caracteres.\n");
        }

        if (quantity.isEmpty()) {
            errorMessage.append("⚠ O campo quantidade deve ser preenchido.\n");
        } else if (quantity.matches("^[0-9]+$")) {
            errorMessage.append("⚠ O Campo só pode aceitar caracteres numéricos.\n");
        }

        if (title.isEmpty()) {
            errorMessage.append("⚠ O campo título deve ser preenchido.\n");
        }

        if (!errorMessage.isEmpty()) {
            AlertHelper.showAlert(errorMessage.toString().trim(), "ERROR");
            return false;
        }

        return true;
    }
}
