package com.example.cryptoapp;

import com.example.cryptoapp.factories.ServiceFactory;
import com.example.cryptoapp.models.User;
import com.example.cryptoapp.models.UserType;
import com.example.cryptoapp.services.IEntityService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsersController extends BaseController{
    @FXML
    private Label headerText;

    @FXML
    private TableView<User> userTableView;

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

    @FXML
    public void initialize() {
        this.userService = ServiceFactory.getInstance().getUserService();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        List<User> users;
        if(userService == null) {
            System.out.println("[!!!!!] Initialize users controller with null userService");
            users = new ArrayList<>();
        }
        else {
            var findAllResult = userService.findAll();
            users = findAllResult != null ? findAllResult : new ArrayList<>();
        }

        ObservableList<User> userList = FXCollections.observableArrayList(users);
        userTableView.setItems(userList);
    }

    @FXML
    public void onShowPortfolioClick() {
        System.out.println("onShowPortfolioClick executed!");

        User selectedUser = userTableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            System.out.println("Selection changed to user with id: " + selectedUser.getId());

            FXMLLoader portfolioLoader = new FXMLLoader(getClass().getResource("portfolios-view.fxml"));
            try{
                portfolioLoader.setControllerFactory(controllerClass -> new PortfoliosController(selectedUser.getId()));
                Parent portfolioParent = portfolioLoader.load();

                Stage portfoliosStage = CreateNewStage("Портфели пользователя", portfolioParent, 500, 200);
                portfoliosStage.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Сначала выберите пользователя в таблице!");
            alert.showAndWait();
        }
    }
}

