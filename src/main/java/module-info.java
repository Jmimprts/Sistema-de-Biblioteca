module br.com.catolicapb.bd2projeto1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires static lombok;
    requires org.hibernate.orm.core;

    opens br.com.catolicapb.bd2projeto1.javafx.controllers to javafx.fxml;
    opens br.com.catolicapb.bd2projeto1.entity to org.hibernate.orm.core, jakarta.persistence, javafx.base, javafx.fxml;

    exports br.com.catolicapb.bd2projeto1.util;
    exports br.com.catolicapb.bd2projeto1.javafx.controllers;
}