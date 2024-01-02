package com.example.cryptoapp;

import com.example.cryptoapp.models.User;
import com.example.cryptoapp.models.Trade;
import com.example.cryptoapp.models.Portfolio;
import com.example.cryptoapp.services.IEntityService;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class BaseController {
    protected IEntityService<User> userService;
    protected IEntityService<Trade> tradeService;
    protected IEntityService<Portfolio> portfolioService;

    public void setUserService(IEntityService<User> userService) {
        this.userService = userService;
    }
    public void setTradeService(IEntityService<Trade> tradeService) {
        this.tradeService = tradeService;
    }
    public void setPortfolioService(IEntityService<Portfolio> portfolioService) {
        this.portfolioService = portfolioService;
    }

    protected Stage CreateNewStage(String title, Parent stageParent, int v, int v1){
        Stage newStage = new Stage();
        newStage.setTitle("Портфели пользователя");
        newStage.setScene(new Scene(stageParent, v, 200));
        return newStage;
    }
}
