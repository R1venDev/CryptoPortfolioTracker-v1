package com.example.cryptoapp;

import com.example.cryptoapp.models.User;
import com.example.cryptoapp.models.Trade;
import com.example.cryptoapp.models.Portfolio;
import com.example.cryptoapp.services.IEntityService;
import com.example.cryptoapp.services.IPortfolioService;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public abstract class BaseController {
    protected IEntityService<User> userService;
    protected IEntityService<Trade> tradeService;
    protected IPortfolioService portfolioService;

    public void setUserService(IEntityService<User> userService) {
        this.userService = userService;
    }
    public void setTradeService(IEntityService<Trade> tradeService) {
        this.tradeService = tradeService;
    }
    public void setPortfolioService(IPortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    protected Stage createNewStage(String title, Parent stageParent, int v, int v1){
        Stage newStage = new Stage();
        newStage.setTitle("Портфели пользователя");
        newStage.setScene(new Scene(stageParent, v, 200));
        return newStage;
    }


    protected void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
