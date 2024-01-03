package com.example.cryptoapp;

public abstract class FormBaseController extends BaseController {
    protected IChildWindowChangesListener listener;

    public FormBaseController(IChildWindowChangesListener listener) {
        this.listener = listener;
    }
}
