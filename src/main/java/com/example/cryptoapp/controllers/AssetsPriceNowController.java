package com.example.cryptoapp.controllers;
import com.example.cryptoapp.BaseController;
import com.example.cryptoapp.IAssetPriceNowListener;
import com.example.cryptoapp.utils.StringDoubleConvertor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

// TODO: Создать разметку с полем ввода AssetsPriceNow, создать под нее AssetsPriceNow controller,
//  контроллер должен принимать в себе единственный listener параметр аналогично portfolioscontroller'y (смотри строки 81-88 как пример как нужно вызвать AssetPriceNow окно),
//  IAssetPriceNowListener listener параметр должен быть в конструкторе контроллера.
//  Логика контроллера: метод OnSaveButtonClick, который будет вызывать listener.onAssetPriceNowChanged с введенной ценой (для конверсии StringDouble использовать StringDoubleConvertor)
//  окно должно закрываться.
public class AssetsPriceNowController extends BaseController {
    private IAssetPriceNowListener listener;

    @FXML
    public TextField assetsPriceNowField;

    @FXML
    public Button saveButton;

    public AssetsPriceNowController(IAssetPriceNowListener listener) {
        this.listener = listener;
    }

    @FXML
    public void onSaveButtonClick() {
        String assetsPriceNowText = assetsPriceNowField.getText();

        try {
            double assetsPriceNow = StringDoubleConvertor.fromString(assetsPriceNowText);
            listener.onAssetPriceNowChanged(assetsPriceNow);

            saveButton.getScene().getWindow().hide();
        } catch (NumberFormatException e) {

            System.out.println("Invalid double format");

        }
    }
}
