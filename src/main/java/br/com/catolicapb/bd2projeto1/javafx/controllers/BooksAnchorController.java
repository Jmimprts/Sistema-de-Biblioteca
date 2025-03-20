package br.com.catolicapb.bd2projeto1.javafx.controllers;

import br.com.catolicapb.bd2projeto1.entity.Livro;
import br.com.catolicapb.bd2projeto1.entity.Usuario;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.LivroRepository;
import br.com.catolicapb.bd2projeto1.javafx.interfaces.IOnChangeScreen;
import br.com.catolicapb.bd2projeto1.util.AlertHelper;
import br.com.catolicapb.bd2projeto1.util.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class BooksAnchorController implements IOnChangeScreen {

    LivroRepository livroRepository = new LivroRepository();

    @FXML
    private ChoiceBox<String> cbFilter;
    @FXML
    private TextField quantityTf;
    @FXML
    private TextField searchTf;
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
    private TableView<Livro> booksTv;

    private final ObservableList<Livro> boooksObservableList = FXCollections.observableArrayList();


    @FXML
    void addBtnAction() {
        String quantity = quantityTf.getText().trim();
        String title = titleTf.getText().trim();
        String year = yearTf.getText().trim();
        String author = authorTf.getText().trim();

        Livro livro = new Livro();
        livro.setQuantidadeDisponivel(Integer.parseInt(quantity));
        livro.setTitulo(title);
        livro.setAnoPublicacao(Integer.parseInt(year));
        livro.setAutor(author);
        livroRepository.addLivro(livro);

        AlertHelper.showAlert("Livro adicionado com sucesso", "INFO");
    }

    @FXML
    public void initialize() {
        ScreenManager.addOnChangeScreenListener(this);

        cbFilter.getItems().addAll("Exibir todos", "Exibir apenas os disponíveis", "Exibir os mais reservados");
        cbFilter.setValue("Exibir todos");

        configureTableColumns();
        loadAllLivros();
    }

    @Override
    public void onScreenChanged(String newScreen) {
        if (newScreen.equals("booksAnchor")) {
            loadAllLivros();
        }
    }

    private void configureTableColumns() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("autor"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("anoPublicacao"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantidadeDisponivel"));
    }

    private void loadAllLivros() {
        //List<Livro> livros = livroRepository.getAllLivros();
        //booksTv.setItems(FXCollections.observableArrayList(livros));
    }
}
