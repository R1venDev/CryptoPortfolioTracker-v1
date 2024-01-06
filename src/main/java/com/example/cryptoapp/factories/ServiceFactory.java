package com.example.cryptoapp.factories;

import com.example.cryptoapp.models.Portfolio;
import com.example.cryptoapp.models.Trade;
import com.example.cryptoapp.models.User;
import com.example.cryptoapp.repositories.GenericRepository;
import com.example.cryptoapp.services.*;
import com.example.cryptoapp.validators.PortfolioValidator;
import com.example.cryptoapp.validators.TradeValidator;
import com.example.cryptoapp.validators.UserValidator;
import org.hibernate.SessionFactory;

public class ServiceFactory {
    private static ServiceFactory instance;

    private final IEntityService<User> userService;
    private final IEntityService<Trade> tradeService;
    private final IPortfolioService portfolioService;

    private ServiceFactory() {
        SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

        var userRepository = new GenericRepository<User>(
                User.class,
                "FROM com.example.cryptoapp.models.User",
                sessionFactory);
        var userValidator = new UserValidator();

        var portfolioRepository = new GenericRepository<Portfolio>(
                Portfolio.class,
                "FROM com.example.cryptoapp.models.Portfolio",
                sessionFactory);
        var portfolioValidator = new PortfolioValidator();

        var tradeRepository = new GenericRepository<Trade>(
                Trade.class,
                "FROM com.example.cryptoapp.models.Trade",
                sessionFactory);
        var tradeValidator = new TradeValidator();

        this.userService = new UserService(userRepository, userValidator);
        this.portfolioService = new PortfolioService(tradeRepository, portfolioRepository, portfolioValidator);
        this.tradeService = new GenericEntityService<Trade>(tradeRepository, tradeValidator);
    }

    public static ServiceFactory getInstance() {
        if (instance == null) {
            synchronized (ServiceFactory.class) {
                if (instance == null) {
                    instance = new ServiceFactory();
                }
            }
        }
        return instance;
    }

    public IEntityService<User> getUserService() {
        return userService;
    }

    public IEntityService<Trade> getTradeService() {
        return tradeService;
    }

    public IPortfolioService getPortfolioService() {
        return portfolioService;
    }
}
