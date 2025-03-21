package br.com.catolicapb.bd2projeto1.javafx.controllers;

import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.*;

import br.com.catolicapb.bd2projeto1.javafx.interfaces.IOnChangeScreen;
import br.com.catolicapb.bd2projeto1.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeAnchorController implements IOnChangeScreen {

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

        loadAllLabels();
    }

    private void loadAllLabels() {
        loadLabelsCountUser();
        loadLabelsCountLoans();
        loadLabelsCountReaders();
        loadLabelCountBooks();
        loadLabelCountReservations();
    }

    private void loadLabelsCountUser() {
        int usersCount = usuarioRepository.getAllUsuarios().size();
        lblUsersCount.setText(String.valueOf(usersCount));
    }

    private void loadLabelsCountLoans(){
        int loansCount = emprestimoRepository.getAllEmprestimos().size();
        lblLoansCount.setText(String.valueOf(loansCount));
    }

    private void loadLabelsCountReaders() {
        int readersCount = leitorRepository.getAllLeitores().size();
        lblReadersCount.setText(String.valueOf(readersCount));
    }

    private void loadLabelCountBooks() {
        int booksCount = livroRepository.getAllLivros().size();
        lblBooksCount.setText(String.valueOf(booksCount));
    }

    private void loadLabelCountReservations() {
        int reservationsCount = reservaRepository.getAllReservas().size();
        lblReservationsCount.setText(String.valueOf(reservationsCount));
    }

    @Override
    public void onScreenChanged(String newScreen) {
        if (newScreen.equals("homeAnchor")) {
            loadAllLabels();
        }
    }
}
