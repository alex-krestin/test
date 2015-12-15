package presentation.controller.users;

import entity.Agency;
import entity.Response;
import entity.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.validation.GridValidator;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.USER;
import static presentation.helper.OperationCode.ADD;
import static presentation.validation.ValidationConstraint.*;
import static presentation.validation.ValidationPattern.*;


public class AddUserController extends UserPopupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeComboBoxes();
    }

    @SuppressWarnings("LawOfDemeter")
    public void handleSaveUserAction() {

        if (addUserValidateInput()) {

            String accountType = fx_account_type.getValue();

            if (Objects.equals(accountType, "Operatore"))
                accountType = "OPERATOR";
            else
                accountType = "ADMIN";

            Agency agency = new Agency.Builder().id(currentAgency.getId()).build();

            User user = new User.Builder()
                    .name(fx_name.getText())
                    .surname(fx_surname.getText())
                    .username(fx_username.getText())
                    .password(fx_password.getText())
                    .accountType(accountType)
                    .agency(agency)
                    .accessGranted(fx_access_granted.isSelected()).build();

            Response response = FrontController.handleRequest(ADD, USER, user);

            if(response.getOperationResult()) {
                closeWindow();
                AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Utente \u00E8 aggiunto correttamente.");
                // Update Users view
                FrontController.handleRequest("Users");
            }

        }
    }

    @SuppressWarnings("LawOfDemeter")
    protected boolean addUserValidateInput() {
        GridValidator gv = new GridValidator(gridPane);
        gv.addConstraint(NOT_EMPTY).addPattern(NAME_PATTERN).validate(fx_name);
        gv.addConstraint(NOT_EMPTY).addPattern(NAME_PATTERN).validate(fx_surname);
        gv.addConstraint(NOT_EMPTY).addPattern(USERNAME_PATTERN).validate(fx_username);
        gv.addConstraint(NOT_EMPTY).addPattern(STRONG_PASSWORD_PATTERN).validate(fx_password);
        gv.addConstraint(NOT_EMPTY).addOption(EQUAL, fx_password).validate(fx_repeat_password);
        gv.addConstraint(NOT_NULL).validate(fx_agencies);

        return gv.isValid();
    }
}
