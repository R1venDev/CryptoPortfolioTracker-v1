package com.example.cryptoapp;

import com.example.cryptoapp.factories.ServiceFactory;
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

public class UsersController extends TableViewBaseController<User>{

    public IEntityService<User> getTableViewItemsService() {
        return this.userService;
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

    @FXML
    public void initialize() {
        this.userService = ServiceFactory.getInstance().getUserService();

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

            FXMLLoader portfolioLoader = new FXMLLoader(getClass().getResource("portfolios-view.fxml"));
            try{
                portfolioLoader.setControllerFactory(controllerClass -> new PortfoliosController(selectedUser.getId()));
                Parent portfolioParent = portfolioLoader.load();

                Stage portfoliosStage = createNewStage("Портфели пользователя", portfolioParent, 500, 200);
                portfoliosStage.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onDeleteUserClick() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {

            userService.deleteById(selectedUser.getId());

            updateTableViewContent();

            showAlert("Успех", "Пользователь успешно удален.");
        } else {
            showAlert("Ошибка", "Выберите пользователя для удаления!");
        }
    }

    public void onPortfoliosButtonClick() {
        try {
            FXMLLoader portfoliosLoader = new FXMLLoader(getClass().getResource("portfolios-view.fxml"));
            Parent portfoliosParent = portfoliosLoader.load();

            Stage portfoliosStage = createNewStage("Портфели", portfoliosParent, 600, 400);
            portfoliosStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}

