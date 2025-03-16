package br.com.catolicapb.bd2projeto1.util;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class ScreenManager extends Application {

    private static Stage stage;
    private static Scene loginScene;
    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.initStyle(StageStyle.UNDECORATED);
        Parent fxmlLogin = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/br/com/catolicapb/bd2projeto1/LoginScreen.fxml")));
        loginScene = new DraggableScene(fxmlLogin);
        Parent fxmlMain = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/br/com/catolicapb/bd2projeto1/MainScreen.fxml")));
        mainScene = new DraggableScene(fxmlMain);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void changeScene(String sceneName) {
        switch (sceneName) {
            case "loginScene":
                stage.setScene(loginScene);
                break;
            case "mainScene":
                stage.setScene(mainScene);
                break;
        }
    }
}
