package it.uniba.carloan.presentation.controller.contracts;


import it.uniba.carloan.entity.Client;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.javafx.scene.control.CustomTextField;
import it.uniba.carloan.presentation.FrontController;
import it.uniba.carloan.presentation.helper.EntityCode;
import it.uniba.carloan.presentation.helper.OperationCode;
import it.uniba.carloan.presentation.validation.GridValidator;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static it.uniba.carloan.business.constants.Constants.MIN_AGE;
import static it.uniba.carloan.presentation.validation.GridValidator.ValidationFlag.ONE;
import static it.uniba.carloan.presentation.validation.ValidationConstraint.*;
import static it.uniba.carloan.presentation.validation.ValidationPattern.*;

public class AddContractClientTabController extends AddContractMainController implements Initializable {

    public CustomTextField fx_search_fiscal_code;
    public TextField fx_name;
    public TextField fx_surname;
    public RadioButton fx_sex_m;
    public ToggleGroup sex;
    public RadioButton fx_sex_f;
    public DatePicker fx_bday;
    public TextField fx_fiscal_code;
    public TextField fx_phone;
    public TextArea fx_comment;
    public GridPane gridPane;

    protected final LocalDate fromDate = LocalDate.now().minusYears(MIN_AGE);

    @SuppressWarnings("LawOfDemeter")
    public void handleSearchAction() {
        fx_name.disableProperty().set(false);
        fx_surname.disableProperty().set(false);
        fx_fiscal_code.disableProperty().set(false);
        fx_phone.disableProperty().set(false);
        fx_bday.disableProperty().set(false);
        fx_sex_m.disableProperty().set(false);
        fx_sex_f.disableProperty().set(false);
        fx_comment.disableProperty().set(false);

        Client client = new Client.Builder()
                .fiscalCode(fx_search_fiscal_code.getText()).build();

        Response response = FrontController.handleRequest(OperationCode.GET, EntityCode.CLIENT, client);
        Client foundClient = (Client) response.getTransferObject();

        if (foundClient != null) {
            fx_name.setText(foundClient.getName());
            fx_surname.setText(foundClient.getSurname());
            fx_fiscal_code.setText(foundClient.getFiscalCode());
            fx_phone.setText(foundClient.getPhoneNumber());
            fx_comment.setText(foundClient.getComment());
            fx_bday.setValue(foundClient.getBirthday());
            // Set sex toggle
            if (foundClient.getSex().equals("M")) {
                fx_sex_m.selectedProperty().setValue(true);
            }
            else {
                fx_sex_f.selectedProperty().setValue(true);
            }

            fx_name.disableProperty().set(true);
            fx_surname.disableProperty().set(true);
            fx_fiscal_code.disableProperty().set(true);
            fx_phone.disableProperty().set(true);
            fx_bday.disableProperty().set(true);
            fx_sex_m.disableProperty().set(true);
            fx_sex_f.disableProperty().set(true);
            fx_comment.disableProperty().set(true);
        }
        else {
            resetTextFields(fx_name, fx_surname, fx_fiscal_code, fx_phone);
            fx_comment.setText(null);
            fx_bday.setValue(null);
            sex.selectToggle(null);
        }
    }

    @SuppressWarnings("LawOfDemeter")
    public boolean validateInput() {
        GridValidator gv = new GridValidator(gridPane);
        gv.addConstraint(NOT_EMPTY).addPattern(NAME_PATTERN).validate(fx_name);
        gv.addConstraint(NOT_EMPTY).addPattern(NAME_PATTERN).validate(fx_surname);
        gv.addConstraint(SELECTED_TOGGLE).addFlag(ONE).validate(fx_sex_f, fx_sex_m);
        gv.addConstraint(NOT_NULL).addOption(IS_BEFORE, fromDate).validate(fx_bday);
        gv.addConstraint(NOT_EMPTY).addPattern(FISCAL_CODE_PATTERN).validate(fx_fiscal_code);
        gv.addConstraint(NOT_EMPTY).addPattern(INTEGER_PATTERN).validate(fx_phone);

        return gv.isValid();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_search_fiscal_code.setMaxLength(16);
        fx_search_fiscal_code.setUppercase();
    }

    @SuppressWarnings("LawOfDemeter")
    public void addClientToContract() {
        RadioButton chk = (RadioButton) sex.getSelectedToggle();
        String clientSex = chk.getText();

        Client client = new Client.Builder()
                .name(fx_name.getText())
                .surname(fx_surname.getText())
                .sex(clientSex)
                .fiscalCode(fx_fiscal_code.getText())
                .birthday(fx_bday.getValue())
                .phoneNumber(fx_phone.getText())
                .comment(fx_comment.getText()).build();

        mainContract.setClient(client);
    }
}
