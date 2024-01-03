package com.example.cryptoapp;

import com.example.cryptoapp.factories.ServiceFactory;
import com.example.cryptoapp.models.Portfolio;
import com.example.cryptoapp.models.User;
import com.example.cryptoapp.models.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PortfoliosController extends BaseController{

    public PortfoliosController(Long selectedUserId){
        this.selectedUserId = selectedUserId;
    }

    @FXML
    private Label headerText;

    protected Long selectedUserId;

    @FXML
    private TableView<Portfolio> portfolioTableView;

    @FXML
    private TableColumn<Portfolio, Long> idColumn;

    @FXML
    private TableColumn<Portfolio, Long> userIdColumn;

    @FXML
    private TableColumn<Portfolio, String> nameColumn;

    @FXML
    private TableColumn<Portfolio, Double> pnlColumn;

    @FXML
    public void initialize() {
        this.portfolioService = ServiceFactory.getInstance().getPortfolioService();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pnlColumn.setCellValueFactory(new PropertyValueFactory<>("pnl"));

        var findAllResult = portfolioService.findAll().stream()
                .filter(x -> x.getUserId() == selectedUserId)
                .collect(Collectors.toList());

        List<Portfolio> portfolios = findAllResult != null ? findAllResult : new ArrayList<>();

        ObservableList<Portfolio> tableList = FXCollections.observableArrayList(portfolios);
        portfolioTableView.setItems(tableList);

        User portfolioOwner = userService.findById(selectedUserId);
        headerText.setText("Портфели пользователя "
                + portfolioOwner.getFirstName() + " "+ portfolioOwner.getLastName());
    }

    @FXML
    public void onShowTradesClick() {
        System.out.println("onShowTradesClick executed!");

        Portfolio selectedPortfolio = portfolioTableView.getSelectionModel().getSelectedItem();

        if (selectedPortfolio != null) {
            System.out.println("Selection changed to Portfolio with id: " + selectedPortfolio.getId());

            FXMLLoader tradeLoader = new FXMLLoader(getClass().getResource("trades-view.fxml"));
            try{
                tradeLoader.setControllerFactory(controllerClass ->
                        new TradesController(selectedPortfolio.getId()));
                Parent portfolioParent = tradeLoader.load();

                Stage portfoliosStage = createNewStage("Портфели пользователя", portfolioParent, 500, 200);
                portfoliosStage.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        else {
            showAlert("Ошибка", "Сначала выберите портфель в таблице!");
        }
    }
}
