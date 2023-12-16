package com.example.cryptoapp;

import com.example.cryptoapp.models.UserType;
import com.example.cryptoapp.services.UserService;
import com.example.cryptoapp.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.hibernate.SessionFactory;

public class HelloController extends BaseController {
    @FXML
    private Label welcomeText;

    //FXML
    //private TextBox firstNameInput;

    // когда загрузил из БД
    // User retrievedUser = userService.getUserById(...)
    // firstNameInput.setText(retrievedUser.getFirstName())

    // при сохранении данных формы (когда хочешь достать значение из формы)
    // String firstName = firstNameInput.getText()
    // String lastName = lastNameInput.getText()
    // User user = new User(..., firstName, lastName, ...)
    // userService.addUser(user)  ||||| userService.updateUser(...) - смотри чтобы была ID

    @FXML
    protected void onHelloButtonClick() {
        userService.addUser(new User(0L, UserType.INVESTOR, "John", "Doe", "johndoe123@m.com"));
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private void onButtonClick() {
        // Обработка события нажатия на кнопку с использованием sessionFactory и entityService
    }
}