package br.com.catolicapb.bd2projeto1.javafx.controllers;

import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.*;

import br.com.catolicapb.bd2projeto1.javafx.interfaces.IOnChangeScreen;
import br.com.catolicapb.bd2projeto1.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeAnchorController implements IOnChangeScreen {

    //SOMENTE TESTES, ALTERAR
    UsuarioRepository usuarioRepository = new UsuarioRepository();
    EmprestimoRepository emprestimoRepository = new EmprestimoRepository();
    LeitorRepository leitorRepository = new LeitorRepository();
    LivroRepository livroRepository = new LivroRepository();
    ReservaRepository reservaRepository = new ReservaRepository();


    @FXML
    private Label lblUsersCount;
    @FXML
    private Label lblLoansCount;
    @FXML
    private Label lblReadersCount;
    @FXML
    private Label lblBooksCount;
    @FXML
    private Label lblReservationsCount;

    @FXML
    public void initialize() {
        ScreenManager.addOnChangeScreenListener(this);

        loadLabelsCountUser();
        loadLabelsCountLoans();
        loadLabelsCountReaders();
        loadLabelCountBooks();
        loadLabelCountReservations();
    }

    public void loadLabelsCountUser() {
        int usersCount = usuarioRepository.getAllUsuarios().size();
        lblUsersCount.setText(String.valueOf(usersCount));
    }

    public void loadLabelsCountLoans(){
        int loansCount = emprestimoRepository.getAllEmprestimos().size();
        lblLoansCount.setText(String.valueOf(loansCount));
    }

    public void loadLabelsCountReaders() {
        int readersCount = leitorRepository.getAllLeitores().size();
        lblReadersCount.setText(String.valueOf(readersCount));
    }

    public void loadLabelCountBooks() {
        int booksCount = livroRepository.getAllLivros().size();
        lblBooksCount.setText(String.valueOf(booksCount));
    }

    public void loadLabelCountReservations() {
        int reservationsCount = reservaRepository.getAllReservas().size();
        lblReservationsCount.setText(String.valueOf(reservationsCount));
    }

    @Override
    public void onScreenChanged(String newScreen) {
        if (newScreen.equals("homeAnchor")) {
            loadLabelsCountUser();
            System.out.println("AnchorPane da home carregada!");
        }
    }
}
