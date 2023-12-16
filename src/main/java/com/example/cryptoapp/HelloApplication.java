package com.example.cryptoapp;

import com.example.cryptoapp.models.Portfolio;
import com.example.cryptoapp.models.Trade;
import com.example.cryptoapp.models.User;
import com.example.cryptoapp.repositories.UserRepository;
import com.example.cryptoapp.services.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.yaml.snakeyaml.Yaml;
import javax.naming.Referenceable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class HelloApplication extends Application {

    private SessionFactory sessionFactory;
    private UserService userService;

    @Override
    public void start(Stage stage) throws IOException {
        configureHibernate();
        sessionFactory.openSession();

        configureEntityServices();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-scene.fxml"));
        Parent parent = loader.load();

        HelloController controller = loader.getController();
        controller.setSessionFactory(sessionFactory);
        controller.setUserService(userService);
        // controller.setUserService(entityService);

        Scene scene = new Scene(parent, 320, 240);
        stage.setTitle("Hello 123!");
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

    private void configureHibernate() {
        try {
            Map<String, Object> config = loadYamlConfiguration();

            Configuration configuration = new Configuration();

            configuration.setProperty("hibernate.connection.url", (String) config.get("hibernate.connection.url"));
            configuration.setProperty("hibernate.connection.driver_class", (String) config.get("hibernate.connection.driver_class"));
            configuration.setProperty("hibernate.connection.username", (String) config.get("hibernate.connection.username"));
            configuration.setProperty("hibernate.connection.password", (String) config.get("hibernate.connection.password"));

            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Trade.class);
            configuration.addAnnotatedClass(Portfolio.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configureEntityServices() {
        var userRepository = new UserRepository(sessionFactory);
        //var tradeRepository = new TradeRepository(sessionFactory);
        //var portfolioRepository = new PortfolioRepository(sessionFactory);

        userService = new UserService(userRepository);
        // ....
    }

    private Map<String, Object> loadYamlConfiguration() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.yml")) {
            Yaml yaml = new Yaml();
            return yaml.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading YAML configuration", e);
        }
    }
}

