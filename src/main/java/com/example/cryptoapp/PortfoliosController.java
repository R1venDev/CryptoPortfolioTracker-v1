package com.example.cryptoapp;

import com.example.cryptoapp.factories.ServiceFactory;
import com.example.cryptoapp.models.Portfolio;
import com.example.cryptoapp.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class PortfoliosController extends BaseController{

    public PortfoliosController(Long selectedUserId){
        this.selectedUserId = selectedUserId;
    }

    @FXML
    private Label headerText;

    protected Long selectedUserId;

    @FXML
    public void initialize() {
        this.portfolioService = ServiceFactory.getInstance().getPortfolioService();

        var findAllResult = portfolioService.findAll(); // TODO: FILTER ALL THESE PORTFOLIOS BY SELECTED USER ID!

        List<Portfolio> portfolios = findAllResult != null ? findAllResult : new ArrayList<>();

        ObservableList<Portfolio> tableList = FXCollections.observableArrayList(portfolios);
        // portfolioTableView.setItems(tableList);

        // TODO: CREATE A TABLE WITH ID portfolioTableView and uncomment the line above!

        headerText.setText("ВЫБРАН: " + selectedUserId); // TODO: REMOVE THIS LINE WHEN UNNECESSARY
    }
}
