package br.com.catolicapb.bd2projeto1.javafx.controllers;

import br.com.catolicapb.bd2projeto1.entity.Usuario;
import br.com.catolicapb.bd2projeto1.infrastructure.Repositories.UsuarioRepository;
import br.com.catolicapb.bd2projeto1.javafx.interfaces.IOnChangeScreen;
import br.com.catolicapb.bd2projeto1.util.AlertHelper;
import br.com.catolicapb.bd2projeto1.util.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class UsersAnchorController implements IOnChangeScreen {

    //SOMENTE TESTES, ALTERAR
    UsuarioRepository usuarioRepository = new UsuarioRepository();

    @FXML
    private TextField emailTf;
    @FXML
    private TextField nameTf;
    @FXML
    private PasswordField pfConfirmPassword;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private TableView<Usuario> usersTv;
    @FXML
    private TableColumn<Usuario, String> emailColumn;
    @FXML
    private TableColumn<Usuario, String> nameColumn;

    private final ObservableList<Usuario> usuarioObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ScreenManager.addOnChangeScreenListener(this);

        configureTableColumns();
        loadUsuarios();
    }

    @FXML
    void addBtnAction() {
        addUser();
    }

    @Override
    public void onScreenChanged(String newScreen) {
        if (newScreen.equals("usersAnchor")) {
            System.out.println("AnchorPane dos usuários carregada!");
        }
    }

    private void configureTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadUsuarios() {

        //SOMENTE TESTES, ALTERAR
        List<Usuario> usuarios = usuarioRepository.getAllUsuarios();

        usuarioObservableList.setAll(usuarios);
        usersTv.setItems(usuarioObservableList);
    }

    private void addUser() {
        String name = nameTf.getText().trim();
        String email = emailTf.getText().trim();
        String password = pfPassword.getText();
        String passwordConfirmation = pfConfirmPassword.getText();

        if (!validateFields(name, email, password, passwordConfirmation)) {
            return;
        }

        //SOMENTE TESTES, ALTERAR
        Usuario usuario = new Usuario();
        usuario.setNome(name);
        usuario.setEmail(email);
        usuario.setSenha(password);
        usuarioRepository.addUsuario(usuario);

        clearFields();
        loadUsuarios();
        AlertHelper.showAlert("Usuário cadastrado com sucesso!", "INFO");
    }

    private boolean validateFields(String name, String email, String password, String passwordConfirmation) {
        StringBuilder errorMessage = new StringBuilder();

        if (name.isEmpty()) {
            errorMessage.append("⚠ O campo Nome deve ser preenchido.\n");
        }

        if (email.isEmpty()) {
            errorMessage.append("⚠ O campo Email deve ser preenchido.\n");
        } else if (!email.matches("^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$")) {
            errorMessage.append("⚠ Formato de e-mail inválido.\n");
        }

        if (password.isEmpty()) {
            errorMessage.append("⚠ O campo Senha deve ser preenchido.\n");
        } else if (password.length() < 6) {
            errorMessage.append("⚠ A senha deve ter pelo menos 6 caracteres.\n");
        }

        if (!password.equals(passwordConfirmation)) {
            errorMessage.append("⚠ As senhas digitadas não coincidem.\n");
        }

        if (errorMessage.length() > 0) {
            AlertHelper.showAlert(errorMessage.toString().trim(), "ERROR");
            return false;
        }

        return true;
    }

    private void clearFields() {
        nameTf.clear();
        emailTf.clear();
        pfConfirmPassword.clear();
        pfPassword.clear();
    }
}
