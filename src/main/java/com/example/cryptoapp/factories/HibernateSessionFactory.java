package com.example.cryptoapp.factories;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactory() {}

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();

            configuration.setProperty("hibernate.current_session_context_class", "thread");
            configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5431/cryptoappdb");
            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.connection.username", "riven");
            configuration.setProperty("hibernate.connection.password", "root");
            configuration.setProperty("hibernate.package_scanner", "com.example.cryptoapp.models");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.setProperty("hibernate.default_schema", "cryptoappschema");

            configuration.addAnnotatedClass(com.example.cryptoapp.models.User.class);
            configuration.addAnnotatedClass(com.example.cryptoapp.models.Trade.class);
            configuration.addAnnotatedClass(com.example.cryptoapp.models.Portfolio.class);

            sessionFactory = configuration.buildSessionFactory();
        }

        return sessionFactory;
    }
}