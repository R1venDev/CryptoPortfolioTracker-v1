package com.example.cryptoapp;

import com.example.cryptoapp.controllers.CreateUpdatePortfolioController;
import com.example.cryptoapp.factories.ServiceFactory;
import com.example.cryptoapp.models.Portfolio;
import com.example.cryptoapp.models.User;
import com.example.cryptoapp.services.IEntityService;
import com.example.cryptoapp.utils.FormType;
import com.example.cryptoapp.viewmodels.PortfolioViewModel;
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

public class PortfoliosController extends TableViewBaseController<PortfolioViewModel>{

    public PortfoliosController(Long selectedUserId, double assetPriceNow) {
        this.selectedUserId = selectedUserId;
        this.assetsPriceNow = assetPriceNow;
    }

    @FXML
    private Label headerText;

    private Long selectedUserId;

    private Double assetsPriceNow;

    @FXML
    private TableColumn<PortfolioViewModel, Long> idColumn;

    @FXML
    private TableColumn<PortfolioViewModel, Long> userIdColumn;

    @FXML
    private TableColumn<PortfolioViewModel, String> nameColumn;

    @FXML
    private TableColumn<PortfolioViewModel, Double> pnlColumn;

    public List<PortfolioViewModel> getTableViewItems() {
        return this.portfolioService.findAll().stream()
                .filter(x -> x.getUserId() == selectedUserId)
                .map(x -> new PortfolioViewModel(x,this.portfolioService.getPNL(x.getId(), assetsPriceNow))).toList();
    }


    @FXML
    public void initialize() {
        this.userService = ServiceFactory.getInstance().getUserService();
        this.portfolioService = ServiceFactory.getInstance().getPortfolioService();
        this.tradeService = ServiceFactory.getInstance().getTradeService();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pnlColumn.setCellValueFactory(new PropertyValueFactory<>("pnl"));

        updateTableViewContent();

        User portfolioOwner = userService.findById(selectedUserId);
        headerText.setText("Портфели пользователя " + portfolioOwner.getFirstName() + " "+ portfolioOwner.getLastName() + "(Текущая цена актива: "+this.assetsPriceNow+")");

    }



    @FXML
    public void onShowTradesClick() {
        System.out.println("onShowTradesClick executed!");

        Portfolio selectedPortfolio = tableView.getSelectionModel().getSelectedItem();

        if (selectedPortfolio != null) {
            System.out.println("Selection changed to Portfolio with id: " + selectedPortfolio.getId());

            FXMLLoader tradeLoader = new FXMLLoader(getClass().getResource("trades-view.fxml"));

            try{
                tradeLoader.setControllerFactory(controllerClass -> new TradesController(selectedPortfolio.getId()));
                Parent tradeParent = tradeLoader.load();

                Stage tradeStage = createNewStage("Сделки портфеля:", tradeParent, 500, 200);
                tradeStage.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        else {
            showAlert("Ошибка", "Сначала выберите портфель в таблице!");
        }
    }

    @FXML
    public void onAddPortfolioClick() {
        try {
            FXMLLoader createPortfolioLoader = new FXMLLoader(getClass().getResource("create-update-portfolios.fxml"));
            createPortfolioLoader.setControllerFactory(controllerClass ->
                    new CreateUpdatePortfolioController(FormType.CREATE, selectedUserId, this));
            Stage createUpdatePortfolioStage = createNewStage("Добавить портфель", createPortfolioLoader.load(), 600, 400);
            createUpdatePortfolioStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onEditPortfolioClick() {
        Portfolio selectedPortfolio = tableView.getSelectionModel().getSelectedItem();

        if (selectedPortfolio != null) {
            try {
                FXMLLoader updatePortfolioLoader = new FXMLLoader(getClass().getResource("create-update-portfolios.fxml"));
                updatePortfolioLoader.setControllerFactory(controllerClass ->
                        new CreateUpdatePortfolioController(FormType.UPDATE, this.selectedUserId, selectedPortfolio.getId(), this));
                Stage updatePortfolioStage = createNewStage("Редактировать портфель", updatePortfolioLoader.load(), 600, 400);
                updatePortfolioStage.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert("Ошибка", "Сначала выберите портфель в таблице!");
        }
    }


    @FXML
    public void onDeletePortfolioClick() {
        Portfolio selectedPortfolio = tableView.getSelectionModel().getSelectedItem();

        if (selectedPortfolio != null) {
            tradeService.findAll().stream()
                            .filter(x -> x.getPortfolioId() == selectedPortfolio.getId())
                    .forEach(x -> tradeService.deleteById(x.getId()));
            portfolioService.deleteById(selectedPortfolio.getId());
            updateTableViewContent();
            showAlert("Успех", "Портфель успешно удален.");
        } else {
            showAlert("Ошибка", "Выберите портфель для удаления!");
        }
    }

}
