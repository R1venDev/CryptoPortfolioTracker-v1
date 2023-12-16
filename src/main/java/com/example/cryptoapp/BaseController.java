package com.example.cryptoapp;

import com.example.cryptoapp.services.UserService;
import org.hibernate.SessionFactory;

public abstract class BaseController {
    protected SessionFactory sessionFactory;
    protected UserService userService;


    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
