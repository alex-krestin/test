package it.uniba.carloan.presentation.controller.users;

import it.uniba.carloan.entity.User;
import it.uniba.carloan.presentation.validation.GridValidator;
import it.uniba.carloan.presentation.validation.ValidationPattern;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.USER;
import static it.uniba.carloan.presentation.validation.ValidationConstraint.NOT_EMPTY;
import static it.uniba.carloan.presentation.validation.ValidationConstraint.NOT_NULL;


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

    @SuppressWarnings("LawOfDemeter")
    public void handleEditUserAction() {
        if (editUserValidateInput()) {

            String accountType = fx_account_type.getValue();

            if (Objects.equals(accountType, "Operatore"))
                accountType = "OPERATOR";
            else
                accountType = "ADMIN";

            User user = new User.Builder()
                    .id(currentUser.getId())
                    .name(fx_name.getText())
                    .surname(fx_surname.getText())
                    .username(fx_username.getText())
                    .accountType(accountType)
                    .agency(fx_agencies.getValue()).build();

            executeUpdate(currentUser, user, USER);
        }
    }

    @SuppressWarnings("LawOfDemeter")
    private boolean editUserValidateInput() {

        GridValidator gv = new GridValidator(gridPane);
        gv.addConstraint(NOT_EMPTY).addPattern(ValidationPattern.NAME_PATTERN).validate(fx_name);
        gv.addConstraint(NOT_EMPTY).addPattern(ValidationPattern.NAME_PATTERN).validate(fx_surname);
        gv.addConstraint(NOT_EMPTY).addPattern(ValidationPattern.USERNAME_PATTERN).validate(fx_username);
        gv.addConstraint(NOT_NULL).validate(fx_agencies);

        return gv.isValid();
    }
}