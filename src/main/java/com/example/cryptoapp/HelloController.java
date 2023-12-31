package com.example.cryptoapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController extends BaseController {
    @FXML
    private Label welcomeText;

    @FXML
    private void onUsersButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("users-view.fxml"));
            Parent root = loader.load();

            Stage secondStage = new Stage();
            secondStage.setTitle("Пользователи (трейдеры)");
            secondStage.setScene(new Scene(root, 500, 200));
            secondStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}