package presentation.controller.users;

import entity.Agency;
import entity.TransferObject;
import entity.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.controller.InputValidationController;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static presentation.controller.InputValidationController.NAME_PATTERN;
import static presentation.controller.InputValidationController.STRONG_PASSWORD_PATTERN;
import static presentation.controller.InputValidationController.USERNAME_PATTERN;
import static presentation.helper.EntityCode.USER;
import static presentation.helper.OperationCode.ADD;


public class AddUserController extends UserPopupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeComboBoxes();
    }

    public void handleSaveUserAction() {

        if (addUserValidateInput()) {

            String name =  fx_name.getText();
            String surname = fx_surname.getText();
            String username = fx_username.getText();
            String password = fx_password.getText();
            String accountType = fx_account_type.getValue();
            Boolean accessGranted = fx_access_granted.isSelected();

            if (Objects.equals(accountType, "Operatore"))
                accountType = "OPERATOR";
            else
                accountType = "ADMIN";

            Agency agency = new Agency(currentAgency.getAgencyID());

            User user = new User(name, surname, username, password, accountType, accessGranted, agency);

            TransferObject response = FrontController.handleRequest(ADD, USER, user);

            if(response.getOperationResult()) {
                closeWindow();
                AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Utente \u00E8 aggiunto correttamente.");
                // Update Users view
                FrontController.handleRequest("Users");
            }

        }
    }

    protected boolean addUserValidateInput() {
        // Reset error message
        fx_error_message.setText(null);

        InputValidationController validator = new InputValidationController();
        // Perform validations
        validator.validateField(NAME_PATTERN, fx_name, fx_name_validation, fx_name_label);
        validator.validateField(NAME_PATTERN, fx_surname, fx_surname_validation, fx_surname_label);
        validator.validateField(USERNAME_PATTERN, fx_username, fx_username_validation, fx_username_label);
        validator.validateField(STRONG_PASSWORD_PATTERN, fx_password, fx_password_validation, fx_password_label);
        validator.validatePasswordRepeat(fx_password, fx_repeat_password, fx_repeat_password_validation,
                fx_password_label, fx_repeat_password_label);

        // Show error message if any
        switch (validator.getNumErrors()) {
            case 0:
                return true;
            default:
                fx_error_message.setText(validator.getFirstError());
                return false;
        }
    }
}
