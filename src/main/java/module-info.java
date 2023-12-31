module com.example.cryptoapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires org.yaml.snakeyaml;
    requires java.naming;
    requires java.sql;
    requires jakarta.persistence;

    opens com.example.cryptoapp.models to org.hibernate.orm.core, javafx.base;
    opens com.example.cryptoapp to javafx.fxml;
    exports com.example.cryptoapp;
}