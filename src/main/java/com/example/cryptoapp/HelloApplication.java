package com.example.cryptoapp;
import com.example.cryptoapp.models.User;
import com.example.cryptoapp.repositories.GenericRepository;
import com.example.cryptoapp.repositories.UserRepository;
import com.example.cryptoapp.services.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.mapping.*;
//import org.yaml.snakeyaml.Yaml;
import java.io.IOException;

public class HelloApplication extends Application {

    private SessionFactory sessionFactory;
    private UserService userService;

    @Override
    public void start(Stage stage) throws IOException {

        SessionFactory sf = configureHibernate();
        System.out.println("SF is null: [" + (sf == null) + "]");

        configureEntityServices(sf);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Parent parent = loader.load();

        HelloController controller = loader.getController();
        controller.setUserService(userService);

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

    private SessionFactory configureHibernate() {
        Configuration configuration = new Configuration();

        configuration.setProperty("hibernate.current_session_context_class", "thread");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5431/postgres");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.package_scanner", "com.example.cryptoapp.models");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        //configuration.setProperty("hibernate.default_schema", "cryptoappschema");
        configuration.addAnnotatedClass(com.example.cryptoapp.models.User.class);
        configuration.addAnnotatedClass(com.example.cryptoapp.models.Trade.class);
        configuration.addAnnotatedClass(com.example.cryptoapp.models.Portfolio.class);

        return configuration.buildSessionFactory();
    }

    /*private SessionFactory configureHibernate() {
        Configuration configuration = new Configuration();

        configuration.setProperty("hibernate.current_session_context_class", "thread");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5431/postgres");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.package_scanner", "com.example.cryptoapp.models");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        final StandardServiceRegistry registry =
            new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        try {
            Metadata metadata = new MetadataSources(registry)
                .addAnnotatedClass(com.example.cryptoapp.models.User.class)
                .addAnnotatedClass(com.example.cryptoapp.models.Trade.class)
                .addAnnotatedClass(com.example.cryptoapp.models.Portfolio.class)
                .buildMetadata();

            System.out.println("Hiberante registered entities (" + metadata.getEntityBindings().size() + ") :");
            for (PersistentClass persClass : metadata.getEntityBindings()) {
                System.out.println("* " + persClass.getClassName());
            }

            return metadata.buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we
            // had trouble building the SessionFactory so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            System.out.println(e.getMessage());
            return null;
        }
    }*/

    private void configureEntityServices(SessionFactory sessionFactory) {
        var userRepository = new GenericRepository<User>(
                User.class,
                "FROM com.example.cryptoapp.models.User",
                sessionFactory);

        //var tradeRepository = new TradeRepository(sessionFactory);
        //var portfolioRepository = new PortfolioRepository(sessionFactory);

        userService = new UserService(userRepository);
        // ....
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

