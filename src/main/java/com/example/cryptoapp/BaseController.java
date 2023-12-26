package com.example.cryptoapp;

import com.example.cryptoapp.services.UserService;

public abstract class BaseController {
    protected UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
