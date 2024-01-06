package com.example.cryptoapp.controllers;

import com.example.cryptoapp.FormBaseController;
import com.example.cryptoapp.IChildWindowChangesListener;
import com.example.cryptoapp.factories.ServiceFactory;
import com.example.cryptoapp.models.Portfolio;
import com.example.cryptoapp.utils.FormType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateUpdatePortfolioController extends FormBaseController {
    public CreateUpdatePortfolioController(FormType type, Long userId, IChildWindowChangesListener listener) {
        super(listener);

        this.type = type;
        this.portfolioId = null;
        this.userId = userId;
    }

    public CreateUpdatePortfolioController(FormType type, Long userId, Long portfolioId, IChildWindowChangesListener listener) {
        super(listener);

        this.type = type;

        if(type == FormType.UPDATE) {
            if(portfolioId == null) {
                throw new NullPointerException(this.getClass().getName() + " has null portfolioId & UPDATE form type");
            }

            this.portfolioId = portfolioId;
            this.userId = userId;
        }
    }

    @FXML
    public void initialize() {

        this.portfolioService = ServiceFactory.getInstance().getPortfolioService();

        if(type == FormType.UPDATE) {
            Portfolio updatedEntity = portfolioService.findById(portfolioId);

            nameTextField.setText(updatedEntity.getName());
            updateSaveButtonText(type);
        }
    }

    @FXML
    private TextField nameTextField;

    private FormType type;

    private Long portfolioId;

    private Long userId;


    @FXML
    private Button saveButton;


    @FXML
    public void onSaveButtonClick() {

        String name = nameTextField.getText();

        if (name.isEmpty()) {
            showAlert("Ошибка", "Заполните все поля!");
            return;
        }

        try {
            Portfolio portfolio = initializePortfolio(userId, name);
            if (type == FormType.CREATE) {
                this.portfolioService.add(portfolio);
            } else {
                portfolio.setId(portfolioId);
                this.portfolioService.update(portfolio);
            }

            closeForm();
            listener.onChildWindowChanges(); // Notify parent window about new User was created!
        } catch (Exception e) {
            showAlert("Ошибка", e.getMessage());
            e.printStackTrace();
        }
    }

    private void closeForm() {
        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.close();
    }

    private Portfolio initializePortfolio(Long userId, String name) {

        return new Portfolio(null, userId, name);
    }

    private void updateSaveButtonText(FormType type)
    {
        String buttonText;
        if (type == FormType.CREATE) {
            buttonText = "Добавить портфель";
        } else {
            buttonText = "Обновить портфель";
        }
        saveButton.setText(buttonText);
    }
}