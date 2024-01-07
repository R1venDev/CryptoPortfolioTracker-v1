package com.example.cryptoapp;

import com.example.cryptoapp.controllers.CreateUpdatePortfolioController;
import com.example.cryptoapp.controllers.CreateUpdateTradeController;
import com.example.cryptoapp.factories.ServiceFactory;
import com.example.cryptoapp.models.Portfolio;
import com.example.cryptoapp.models.Trade;
import com.example.cryptoapp.models.User;
import com.example.cryptoapp.utils.FormType;
import com.example.cryptoapp.viewmodels.PortfolioViewModel;
import com.example.cryptoapp.viewmodels.TradeViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TradesController extends TableViewBaseController<TradeViewModel> {
    public TradesController(Long selectedPortfolioId, double assetPriceNow, ITradesListener listener){
        this.selectedPortfolioId = selectedPortfolioId;
        this.assetsPriceNow = assetPriceNow;
        this.listener = listener;

    }

    @FXML
    private Label headerText;

    public final ITradesListener listener;

    private Double assetsPriceNow;

    protected Long selectedPortfolioId;

    @FXML
    private TableColumn<TradeViewModel, Long> idColumn;

    @FXML
    private TableColumn<TradeViewModel, Long> portfolioIdColumn;

    @FXML
    private TableColumn<TradeViewModel, Date> startDateColumn;

    @FXML
    private TableColumn<TradeViewModel, Date> endDateColumn;

    @FXML
    private TableColumn<TradeViewModel, String> tradeStatusColumn;

    @FXML
    private TableColumn<TradeViewModel, String> tradeResultColumn;

    @FXML
    private TableColumn<TradeViewModel, Double> tradeAmountColumn;

    @FXML
    private TableColumn<TradeViewModel, Double> pnlColumn;

    @FXML
    private TableColumn<TradeViewModel, Double> assetQuantityColumn;

    @FXML
    private TableColumn<TradeViewModel, Double> assetPriceColumn;

    @FXML
    private TableColumn<TradeViewModel, Double> leverageColumn;

    public List<TradeViewModel> getTableViewItems() {
        List<TradeViewModel> list = this.tradeService.findAll().stream()
                .filter(x -> x.getPortfolioId() == selectedPortfolioId)
                .map(x -> new TradeViewModel(x,assetsPriceNow)).toList();
        return list;
    }

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
        tradeResultColumn.setCellValueFactory(new PropertyValueFactory<>("tradeResult"));
        pnlColumn.setCellValueFactory(new PropertyValueFactory<>("pnl"));
        assetQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("assetQuantity"));
        assetPriceColumn.setCellValueFactory(new PropertyValueFactory<>("assetPrice"));
        tradeAmountColumn.setCellValueFactory(new PropertyValueFactory<>("tradeAmount"));
        leverageColumn.setCellValueFactory(new PropertyValueFactory<>("leverage"));

        updateTableViewContent();

        Portfolio selectedPortfolio = portfolioService.findById(selectedPortfolioId);
        User portfolioOwner = userService.findById(selectedPortfolio.getUserId());
        headerText.setText("Сделки портфеля " + selectedPortfolio.getName() + " пользователя "
                + portfolioOwner.getFirstName() + " "+ portfolioOwner.getLastName() + " (Текущая цена актива: "+this.assetsPriceNow+")");
    }


    public void updateTableViewContent() {
        super.updateTableViewContent();
        listener.onTradesWindowChanges();
    }



    @FXML
    public void onAddTradeClick() {
        try {
            FXMLLoader createTradeLoader = new FXMLLoader(getClass().getResource("create-update-trades.fxml"));
            createTradeLoader.setControllerFactory(controllerClass ->
                    new CreateUpdateTradeController(FormType.CREATE, selectedPortfolioId, this));
            Stage createUpdateTradeStage = createNewStage("Добавить сделку", createTradeLoader.load(), 600, 400);
            createUpdateTradeStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onEditTradeClick() {
        Trade selectedTrade = tableView.getSelectionModel().getSelectedItem();

        if (selectedTrade != null) {
            try {
                FXMLLoader updateTradeLoader = new FXMLLoader(getClass().getResource("create-update-trades.fxml"));
                updateTradeLoader.setControllerFactory(controllerClass ->
                        new CreateUpdateTradeController(FormType.UPDATE, this.selectedPortfolioId, selectedTrade.getId(), this));
                Stage updateTradeStage = createNewStage("Редактировать сделки", updateTradeLoader.load(), 600, 400);
                updateTradeStage.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert("Ошибка", "Сначала выберите сделку в таблице!");
        }
    }


    @FXML
    public void onDeleteTradeClick() {
        Trade selectedTrade = tableView.getSelectionModel().getSelectedItem();

        if (selectedTrade != null) {
            tradeService.deleteById(selectedTrade.getId());
            updateTableViewContent();
            showAlert("Успех", "Сделка успешно удалена.");
        } else {
            showAlert("Ошибка", "Выберите сделку для удаления!");
        }
    }


}
