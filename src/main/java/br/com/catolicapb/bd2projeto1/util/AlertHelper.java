package br.com.catolicapb.bd2projeto1.util;

import javafx.scene.control.Alert;

public class AlertHelper {

    public static void showAlert(String msg, String type) {
        Alert alert;
        switch (type.toUpperCase()) {
            case "ERROR" -> {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
            }
            case "INFO" -> {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informação");
            }
            case "WARNING" -> {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
            }
            case "CONFIRMATION" -> {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmação");
            }
            default -> {
                alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle("Notificação");
            }
        }
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
