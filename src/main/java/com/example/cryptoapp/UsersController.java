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
            /*userService.add(
                    new User(123L, UserType.INVESTOR,
                            "Investorov", "Ivan",
                            "invivan@mail.box"));
            userService.add(
                    new User(4456L, UserType.TRADER,
                            "Traderova", "Irina",
                            "irinatr@mail.box"));*/

            var findAllResult = userService.findAll();
            users = findAllResult != null ? findAllResult : new ArrayList<>();
        }

        ObservableList<User> userList = FXCollections.observableArrayList(users);
        userTableView.setItems(userList);
    }

    @FXML
    public void onShowPortfolioClick() {
        userTableView.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSelectedUser, newSelectedUser) -> {
            if (newSelectedUser != null) {
                FXMLLoader portfoliosLoader = new FXMLLoader(getClass().getResource("portfolios-view.fxml"));
                FXMLLoader portfolioLoader = new FXMLLoader(getClass().getResource("portfolios-view.fxml"));
                try {
                    Parent portfolioParent = portfolioLoader.load();
                    PortfoliosController controller = portfolioLoader.getController();
                    controller.setSelectedUserId(newSelectedUser.getId());

                    Stage portfoliosStage = new Stage();
                    portfoliosStage.setTitle("Портфели пользователя");
                    portfoliosStage.setScene(new Scene(portfolioParent, 500, 200));
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
        });
    }
}

