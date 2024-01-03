package com.example.cryptoapp;

import com.example.cryptoapp.factories.ServiceFactory;
import com.example.cryptoapp.models.Portfolio;
import com.example.cryptoapp.models.Trade;
import com.example.cryptoapp.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TradesController extends BaseController{
    public TradesController(Long selectedPortfolio){
        this.selectedPortfolioId = selectedPortfolioId;
    }

    @FXML
    private Label headerText;

    protected Long selectedPortfolioId;

    @FXML
    private TableView<Trade> tradeTableView;

    @FXML
    private TableColumn<Trade, Long> idColumn;

    @FXML
    private TableColumn<Trade, Long> portfolioIdColumn;

    @FXML
    private TableColumn<Portfolio, Date> startDateColumn;

    @FXML
    private TableColumn<Portfolio, Date> endDateColumn;

    @FXML
    private TableColumn<Portfolio, String> tradeStatusColumn;

    @FXML
    private TableColumn<Portfolio, String> tradeStateColumn;

    @FXML
    private TableColumn<Portfolio, Double> assetQuantityColumn;

    @FXML
    private TableColumn<Portfolio, Double> assetPriceColumn;

    @FXML
    private TableColumn<Portfolio, Double> assetAmountColumn;

    @FXML
    private TableColumn<Portfolio, Double> leverageColumn;

    @FXML
    public void initialize() {
        this.userService = ServiceFactory.getInstance().getUserService();
        this.portfolioService = ServiceFactory.getInstance().getPortfolioService();
        this.tradeService = ServiceFactory.getInstance().getTradeService();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        portfolioIdColumn.setCellValueFactory(new PropertyValueFactory<>("portfolioId"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        tradeStatusColumn.setCellValueFactory(new PropertyValueFactory<>("tradeStatus"));
        tradeStateColumn.setCellValueFactory(new PropertyValueFactory<>("tradeState"));
        assetQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("assetQuantity"));
        assetPriceColumn.setCellValueFactory(new PropertyValueFactory<>("assetPrice"));
        assetAmountColumn.setCellValueFactory(new PropertyValueFactory<>("assetAmount"));
        leverageColumn.setCellValueFactory(new PropertyValueFactory<>("leverage"));

        var findAllResult = tradeService.findAll().stream()
                .filter(x -> x.getPortfolioId() == selectedPortfolioId)
                .collect(Collectors.toList());

        List<Trade> trades = findAllResult != null ? findAllResult : new ArrayList<>();

        ObservableList<Trade> tableList = FXCollections.observableArrayList(trades);
        tradeTableView.setItems(tableList);

        Portfolio selectedPortfolio = portfolioService.findById(selectedPortfolioId);
        User portfolioOwner = userService.findById(selectedPortfolio.getUserId());
        headerText.setText("Портфель '" + selectedPortfolio.getName() + "' пользователя "
                + portfolioOwner.getFirstName() + " "+ portfolioOwner.getLastName());
    }
}
