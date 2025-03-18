module br.com.catolicapb.bd2projeto1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires static lombok;
    requires org.hibernate.orm.core;

    opens br.com.catolicapb.bd2projeto1.controller to javafx.fxml;
    opens br.com.catolicapb.bd2projeto1.entity to org.hibernate.orm.core, jakarta.persistence;

    exports br.com.catolicapb.bd2projeto1.util;
    exports br.com.catolicapb.bd2projeto1.controller;
}