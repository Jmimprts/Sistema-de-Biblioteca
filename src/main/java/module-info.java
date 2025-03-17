module br.com.catolicapb.bd2projeto1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires static lombok;

    opens br.com.catolicapb.bd2projeto1.controller to javafx.fxml;
    exports br.com.catolicapb.bd2projeto1.util;
    exports br.com.catolicapb.bd2projeto1.controller;
}