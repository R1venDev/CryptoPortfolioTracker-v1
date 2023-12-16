module com.example.cryptoapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires org.yaml.snakeyaml;
    requires java.naming;


    opens com.example.cryptoapp to javafx.fxml;
    exports com.example.cryptoapp;
}