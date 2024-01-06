package com.example.cryptoapp;

import com.example.cryptoapp.controllers.AssetsPriceNowController;
import com.example.cryptoapp.factories.ServiceFactory;
import com.example.cryptoapp.models.BaseModel;
import com.example.cryptoapp.models.User;
import com.example.cryptoapp.models.UserType;
import com.example.cryptoapp.services.IEntityService;
import com.example.cryptoapp.utils.FormType;
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

public class UsersController extends TableViewBaseController<User> implements IAssetPriceNowListener{

    public List<User> getTableViewItems() {
        return this.userService.findAll();
    }

    @FXML
    private Label headerText;

    @FXML
    private TableColumn<User, Long> idColumn;

    @FXML
    private TableColumn<User, UserType> userTypeColumn;

    @FXML
    private TableColumn<User, String> firstNameColumn;

    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    private double assetPriceNow;

    @FXML
    public void initialize() {
        this.userService = ServiceFactory.getInstance().getUserService();
        this.portfolioService = ServiceFactory.getInstance().getPortfolioService();
        this.tradeService = ServiceFactory.getInstance().getTradeService();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        updateTableViewContent();
    }

    @FXML
    public void onShowPortfolioClick() {
        System.out.println("onShowPortfolioClick executed!");

        User selectedUser = tableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            System.out.println("Selection changed to user with id: " + selectedUser.getId());
// TODO: Создать разметку с полем ввода AssetsPriceNow, создать под нее AssetsPriceNow controller,
//  контроллер должен принимать в себе единственный listener параметр аналогично portfolioscontroller'y (смотри строки 81-88 как пример как нужно вызвать AssetPriceNow окно),
//  IAssetPriceNowListener listener параметр должен быть в конструкторе контроллера.
//  Логика контроллера: метод OnSaveButtonClick, который будет вызывать listener.onAssetPriceNowChanged с введенной ценой (для конверсии StringDouble использовать StringDoubleConvertor)
//  окно должно закрываться.
            
            try {
                FXMLLoader assetsPriceNowLoader = new FXMLLoader(getClass().getResource("assets-price-now-view.fxml"));
                assetsPriceNowLoader.setControllerFactory(controllerClass ->
                        new AssetsPriceNowController(this));
                Parent assetsPriceNowParent = assetsPriceNowLoader.load();

                Stage assetsPriceNowStage = createNewStage("Assets Price Now", assetsPriceNowParent, 300, 200);
                assetsPriceNowStage.show();
                
            } catch (IOException e) {
                showAlert("Ошибка", e.getMessage());
                e.printStackTrace();
            }
        }
        else {
            showAlert("Ошибка", "Сначала выберите пользователя в таблице!");
        }
    }

    public void onAddUserClick() {
        try {
            FXMLLoader createUserLoader = new FXMLLoader(getClass().getResource("create-update-users.fxml"));
            createUserLoader.setControllerFactory(controllerClass ->
                    new CreateUpdateUsersController(FormType.CREATE, this));
            Parent createUpdateUsersParent = createUserLoader.load();
            Stage createUpdateUsersStage = createNewStage("Создать пользователя", createUpdateUsersParent, 600, 400);
            createUpdateUsersStage.show();
        } catch (IOException e) {
            showAlert("Ошибка", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onEditUserClick() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();

        try {
            FXMLLoader updateUserLoader = new FXMLLoader(getClass().getResource("create-update-users.fxml"));
            updateUserLoader.setControllerFactory(controllerClass ->
                    new CreateUpdateUsersController(FormType.UPDATE, selectedUser.getId(), this));
            Parent createUpdateUsersParent = updateUserLoader.load();
            Stage createUpdateUsersStage = createNewStage("Обновить пользователя " + selectedUser.getEmail(), createUpdateUsersParent, 600, 400);
            createUpdateUsersStage.show();
        } catch (IOException e) {
            showAlert("Ошибка", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onDeleteUserClick() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            List <Long> userPortfolioIds = portfolioService.findAll().stream()
                    .filter(x -> x.getUserId() == selectedUser.getId())
                            .map(BaseModel::getId).toList();
            for (Long portfolioId:userPortfolioIds) {
                tradeService.findAll().stream()
                        .filter(x -> x.getPortfolioId() == portfolioId)
                        .forEach(x -> tradeService.deleteById(x.getId()));
                portfolioService.deleteById(portfolioId);
            }


            userService.deleteById(selectedUser.getId());

            updateTableViewContent();

            showAlert("Успех", "Пользователь успешно удален.");
        } else {
            showAlert("Ошибка", "Выберите пользователя для удаления!");
        }
    }

    @Override
    public void onAssetPriceNowChanged(double newAssetPriceNow) {
        this.assetPriceNow = newAssetPriceNow;
        
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        
        try {
            FXMLLoader portfolioLoader = new FXMLLoader(getClass().getResource("portfolios-view.fxml"));

            portfolioLoader.setControllerFactory(controllerClass ->
                    new PortfoliosController(selectedUser.getId(),this.assetPriceNow));
            Parent portfolioParent = portfolioLoader.load();

            Stage portfoliosStage = createNewStage("Портфели пользователя", portfolioParent, 500, 200);
            portfoliosStage.show(); }
        catch (IOException e) {
            showAlert("Ошибка", e.getMessage());
            e.printStackTrace();
        }
    }
}

