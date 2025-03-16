package br.com.catolicapb.bd2projeto1.util;

import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DraggableScene extends Scene {

    private double xOffset = 0;
    private double yOffset = 0;


    public DraggableScene(Parent root) {
        super(root);
        addDragListeners();
    }

    private void addDragListeners() {
        this.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
            setCursor(Cursor.MOVE);
        });
        this.setOnMouseDragged(event -> {
            Stage stage = (Stage) this.getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        this.setOnMouseReleased(event -> setCursor(Cursor.DEFAULT));
    }
}
