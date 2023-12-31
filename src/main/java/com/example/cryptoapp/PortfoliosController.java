package com.example.cryptoapp;

import com.example.cryptoapp.factories.ServiceFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PortfoliosController extends BaseController{
    @FXML
    private Label headerText;

    protected Long selectedUserId;

    public void setSelectedUserId(Long id) {
        this.selectedUserId = id;
    }

        @FXML
        public void initialize() {
            this.portfolioService = ServiceFactory.getInstance().getPortfolioService();
            headerText.setText("ВЫБРАН: " + selectedUserId);
    }
}
