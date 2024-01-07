package com.example.cryptoapp.controllers;

import com.example.cryptoapp.FormBaseController;
import com.example.cryptoapp.IChildWindowChangesListener;
import com.example.cryptoapp.factories.ServiceFactory;
import com.example.cryptoapp.models.*;
import com.example.cryptoapp.utils.EnumConvertor;
import com.example.cryptoapp.utils.FormType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateUpdateTradeController  extends FormBaseController {


    @FXML
    private TextField startDateTextField;

    @FXML
    private TextField endDateTextField;

    @FXML
    private TextField assetsQuantityTextField;

    @FXML
    private TextField assetsPriceTextField;

    @FXML
    private TextField leverageTextField;



    @FXML
    private ComboBox<String> tradeStatusComboBox;
    @FXML
    private ComboBox<String> tradeTypeComboBox;
    @FXML
    private ComboBox<String> tradeResultComboBox;


    @FXML
    private Button saveButton;

    private FormType type;

    private Long tradeId;

    private Long portfolioId;


    public CreateUpdateTradeController(FormType type, Long portfolioId, IChildWindowChangesListener listener) {
        super(listener);
        this.type = type;
        this.tradeId = null;
        this.portfolioId = portfolioId;
    }

    public CreateUpdateTradeController(FormType type, Long portfolioId, Long tradeId, IChildWindowChangesListener listener) {
        super(listener);
        this.type = type;

        if(type == FormType.UPDATE) {
            if(portfolioId == null) {
                throw new NullPointerException(this.getClass().getName() + " has null portfolioId & UPDATE form type");
            }

            this.tradeId = tradeId;
            this.portfolioId = portfolioId;
        }
    }

    @FXML
    public void initialize() {
        tradeStatusComboBox.setItems(FXCollections.observableArrayList(EnumConvertor.enumToStringList(TradeStatus.class)));
        tradeStatusComboBox.getSelectionModel().selectFirst();
        tradeResultComboBox.setItems(FXCollections.observableArrayList(EnumConvertor.enumToStringList(TradeResult.class)));
        tradeResultComboBox.getSelectionModel().selectFirst();
        tradeTypeComboBox.setItems(FXCollections.observableArrayList(EnumConvertor.enumToStringList(TradeType.class)));
        tradeTypeComboBox.getSelectionModel().selectFirst();

        this.tradeService = ServiceFactory.getInstance().getTradeService();

        if(type == FormType.UPDATE) {
            Trade updatedTrade = tradeService.findById(tradeId);

            startDateTextField.setText(updatedTrade.getStartDate().toString());
            endDateTextField.setText(updatedTrade.getEndDate().toString());
            assetsQuantityTextField.setText(String.valueOf(updatedTrade.getAssetQuantity()));
            assetsPriceTextField.setText(String.valueOf(updatedTrade.getAssetPrice()));
            leverageTextField.setText(String.valueOf(updatedTrade.getLeverage()));
            tradeTypeComboBox.setValue(updatedTrade.getTradeType().toString());
            tradeResultComboBox.setValue(updatedTrade.getTradeResult().toString());
            tradeStatusComboBox.setValue(updatedTrade.getTradeStatus().toString());
            updateSaveButtonText(type);
        }
    }


    @FXML
    public void onSaveButtonClick() {
        try {
            Date startDate = parseDate(startDateTextField.getText());
            Date endDate = parseDate(endDateTextField.getText());
            double assetQuantity = Double.parseDouble(assetsQuantityTextField.getText());
            double assetPrice = Double.parseDouble(assetsPriceTextField.getText());
            double leverage = Double.parseDouble(leverageTextField.getText());

            Trade trade = initializeTrade(portfolioId, startDate, endDate,
                    EnumConvertor.stringValueToEnum(TradeStatus.class, tradeStatusComboBox.getValue()),
                    EnumConvertor.stringValueToEnum(TradeResult.class, tradeResultComboBox.getValue()),
                    EnumConvertor.stringValueToEnum(TradeType.class, tradeTypeComboBox.getValue()),
                    assetQuantity, assetPrice, leverage);

            if (type == FormType.CREATE) {
                this.tradeService.add(trade);
            } else {
                trade.setId(tradeId);
                this.tradeService.update(trade);
            }

            closeForm();
            listener.onChildWindowChanges(); // Notify parent window about new Trade was created/updated!
        } catch (Exception e) {
            showAlert("Ошибка", e.getMessage());
            e.printStackTrace();
        }
    }


    private Trade initializeTrade(Long portfolioId, Date startDate, Date endDate, TradeStatus tradeStatus,
                                  TradeResult tradeState, TradeType tradeType, double assetQuantity,
                                  double assetPrice, double leverage) {
        return new Trade(null, portfolioId, startDate, endDate,
                tradeStatus, tradeState, tradeType, assetQuantity, assetPrice, leverage);
    }

    private Date parseDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return dateString.isEmpty() ? null : sdf.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing date: " + dateString, e);
        }
    }

    private void closeForm() {
        Stage stage = (Stage) startDateTextField.getScene().getWindow();
        stage.close();
    }

    private void updateSaveButtonText(FormType type)
    {
        String buttonText;
        if (type == FormType.CREATE) {
            buttonText = "Добавить сделку";
        } else {
            buttonText = "Обновить сделку";
        }
        saveButton.setText(buttonText);
    }
}
