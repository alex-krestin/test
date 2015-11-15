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
import static presentation.controller.InputValidationController.USERNAME_PATTERN;
import static presentation.helper.EntityCode.USER;
import static presentation.helper.OperationCode.UPDATE;


public class EditUserController extends UserPopupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeComboBoxes();

        fx_name.setText(currentUser.getName());
        fx_surname.setText(currentUser.getSurname());
        fx_username.setText(currentUser.getUsername());
        if (Objects.equals(currentUser.getAccountType(), "OPERATOR"))
            fx_account_type.getSelectionModel().select("Operatore");
        else
            fx_account_type.getSelectionModel().select("Amministratore");

        fx_agencies.getSelectionModel().select(currentUser.getAgency());
    }

    public void handleEditUserAction() {
        if (editUserValidateInput()) {

            String name =  fx_name.getText();
            String surname = fx_surname.getText();
            String username = fx_username.getText();
            String accountType = fx_account_type.getValue();
            Agency agency = fx_agencies.getValue();

            if (Objects.equals(accountType, "Operatore"))
                accountType = "OPERATOR";
            else
                accountType = "ADMIN";

            User user = new User(currentUser.getId(), name, surname, username, accountType, agency);

            // Update user only if there are any changes
            if(!user.equals(currentUser)) {
                TransferObject response = FrontController.handleRequest(UPDATE, USER, user);

                if(response.getOperationResult()) {
                    closeWindow();
                    AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Utente \u00E8 succesivamente aggiornato.");
                    // Update Users view
                    FrontController.handleRequest("Users");
                }
            }
            else {
                closeWindow();
            }
        }
    }

    private boolean editUserValidateInput() {
        // Reset error message and errors array
        fx_error_message.setText(null);

        InputValidationController validator = new InputValidationController();
        // Perform validations
        validator.validateField(NAME_PATTERN, fx_name, fx_name_validation, fx_name_label);
        validator.validateField(NAME_PATTERN, fx_surname, fx_surname_validation, fx_surname_label);
        validator.validateField(USERNAME_PATTERN, fx_username, fx_username_validation, fx_username_label);

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