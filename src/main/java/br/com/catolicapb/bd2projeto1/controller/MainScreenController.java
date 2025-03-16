package br.com.catolicapb.bd2projeto1.controller;

import br.com.catolicapb.bd2projeto1.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainScreenController {

    @FXML
    private AnchorPane booksAnchor;
    @FXML
    private AnchorPane homeAnchor;
    @FXML
    private AnchorPane loansAnchor;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private AnchorPane readersAnchor;
    @FXML
    private AnchorPane usersAnchor;
    @FXML
    private Button homeBtn;
    @FXML
    private Button usersBtn;
    @FXML
    private Button readersBtn;
    @FXML
    private Button booksBtn;
    @FXML
    private Button loansBtn;

    private AnchorPane currentAnchor;
    private Button currentButton;

    @FXML
    protected void initialize() {
        currentAnchor = homeAnchor;
        currentButton = homeBtn;

        homeAnchor.setVisible(true);
        usersAnchor.setVisible(false);
        readersAnchor.setVisible(false);
        booksAnchor.setVisible(false);
        loansAnchor.setVisible(false);

        homeBtn.getStyleClass().add("active");
    }

    @FXML
    void btLogoutAction() {
        logoutProcedure();
    }

    @FXML
    void closeBtnAction() {
        close();
    }

    @FXML
    void minimizeBtnAction() {
        minimize();
    }

    @FXML
    void homeBtnAction() {
        showAnchor(homeAnchor, homeBtn);
    }

    @FXML
    void usersBtnAction() {
        showAnchor(usersAnchor, usersBtn);
    }

    @FXML
    void readersBtnAction() {
        showAnchor(readersAnchor, readersBtn);
    }

    @FXML
    void booksBtnAction() {
        showAnchor(booksAnchor, booksBtn);
    }

    @FXML
    void loansBtnAction() {
        showAnchor(loansAnchor, loansBtn);
    }

    private void logoutProcedure() {
        ScreenManager.changeScene("loginScene");
        showAnchor(homeAnchor, homeBtn);
    }

    private void close() {
        System.exit(0);
    }

    private void minimize() {
        Stage stage = (Stage) mainAnchor.getScene().getWindow();
        stage.setIconified(true);
    }

    private void showAnchor(AnchorPane anchorToShow, Button buttonToFocus) {
        if (anchorToShow != currentAnchor) {
            currentAnchor.setVisible(false);
            currentButton.getStyleClass().remove("active");
            anchorToShow.setVisible(true);
            buttonToFocus.getStyleClass().add("active");
            currentAnchor = anchorToShow;
            currentButton = buttonToFocus;
        }
    }
}
