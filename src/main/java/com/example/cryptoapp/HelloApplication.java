package com.example.cryptoapp;
import com.example.cryptoapp.factories.HibernateSessionFactory;
import com.example.cryptoapp.factories.ServiceFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
//import org.yaml.snakeyaml.Yaml;
import java.io.IOException;

public class HelloApplication extends Application {
    private SessionFactory sessionFactory;

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("SF is null: [" + (this.sessionFactory == null) + "]");

        this.sessionFactory = HibernateSessionFactory.getSessionFactory();
        ServiceFactory sf = ServiceFactory.getInstance();

        FXMLLoader helloLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Parent helloParent = helloLoader.load();
        Scene scene = new Scene(helloParent, 320, 240);
        stage.setTitle("Добро пожаловать!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
        super.stop();
    }

    /*private Map<String, Object> loadYamlConfiguration() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.yml")) {
            Yaml yaml = new Yaml();
            return yaml.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading YAML configuration", e);
        }
    }*/
}

