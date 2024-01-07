package com.example.cryptoapp;
import com.example.cryptoapp.factories.ServiceFactory;
import com.example.cryptoapp.models.User;
import com.example.cryptoapp.models.UserType;
import com.example.cryptoapp.utils.EnumConvertor;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import com.example.cryptoapp.utils.FormType;
import javafx.stage.Stage;

public class CreateUpdateUsersController extends FormBaseController {
    public CreateUpdateUsersController(FormType type, IChildWindowChangesListener listener) {
        super(listener);

        this.type = type;
        this.userId = null;
    }

    public CreateUpdateUsersController(FormType type, Long userId, IChildWindowChangesListener listener) {
        super(listener);

        this.type = type;

        if(type == FormType.UPDATE) {
            if(userId == null) {
                throw new NullPointerException("CreateUpdateUsers controller has null userId & UPDATE form type");
            }

            this.userId = userId;
        }
    }

    @FXML
    public void initialize() {
        userTypeComboBox.setItems(FXCollections.observableArrayList(EnumConvertor.enumToStringList(UserType.class)));
        userTypeComboBox.getSelectionModel().selectFirst();

        this.userService = ServiceFactory.getInstance().getUserService();
        
        System.out.println("CreateUpdateUsers controller initialized with null user service: " + (this.userService == null));

        if(type == FormType.UPDATE) {
            User updatedUser = userService.findById(userId);

            firstNameTextField.setText(updatedUser.getFirstName());
            lastNameTextField.setText(updatedUser.getLastName());
            emailTextField.setText(updatedUser.getEmail());
            userTypeComboBox.setValue(updatedUser.getUserType().toString());

            updateSaveButtonText(type);
        }
    }

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private ComboBox<String> userTypeComboBox;
    private FormType type;

    private Long userId;


    @FXML
    private Button saveButton;


    @FXML
    public void onSaveButtonClick() {
        System.out.println("CreateUpdateUsers controller onSave called with null user service: " + (this.userService == null));

        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String userType = userTypeComboBox.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || userType == null) {
            showAlert("Ошибка", "Заполните все поля!");
            return;
        }

        try {
            if (type == FormType.CREATE) {
                User userToAdd = initializeUser(userType, firstName, lastName, email);
                System.out.println("OUT CREATE!");
                this.userService.add(userToAdd);
            } else {
                User userToUpdate = initializeUser(userType, firstName, lastName, email);
                userToUpdate.setId(userId);
                this.userService.update(userToUpdate);
            }

            closeForm();
            listener.onChildWindowChanges(); // Notify parent window about new User was created!
        } catch (Exception e) {
            showAlert("Ошибка", e.getMessage());
            e.printStackTrace();
        }
    }

    private void closeForm() {
        Stage stage = (Stage) firstNameTextField.getScene().getWindow();
        stage.close();
    }

    private User initializeUser(String userType, String firstName, String lastName, String email) {

        User user = new User();
        user.setUserType(EnumConvertor.stringValueToEnum(UserType.class, userType));

        if (firstName != null) {
            user.setFirstName(firstName);
        }

        if (lastName != null) {
            user.setLastName(lastName);
        }

        if (email != null) {
            user.setEmail(email);
        }

        return user;
    }

    private void updateSaveButtonText(FormType type)
    {
        String buttonText;
        if (type == FormType.CREATE) {
            buttonText = "Добавить пользователя";
        } else {
            buttonText = "Обновить пользователя";
        }
        saveButton.setText(buttonText);
    }
}
