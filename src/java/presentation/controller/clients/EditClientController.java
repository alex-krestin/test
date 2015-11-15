package presentation.controller.clients;

import entity.Client;
import entity.TransferObject;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import presentation.AlertHandler;
import presentation.FrontController;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import static presentation.controller.InputValidationController.disableDateSelection;
import static presentation.helper.EntityCode.CLIENT;
import static presentation.helper.OperationCode.UPDATE;


public class EditClientController extends ClientPopupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableDateSelection(fromDate, null, fx_bdate);

        fx_name.setText(currentClient.getName());
        fx_surname.setText(currentClient.getSurname());
        fx_fiscal_code.setText(currentClient.getFiscalCode());
        fx_phone.setText(currentClient.getPhoneNumber());
        fx_comment.setText(currentClient.getComment());
        // Convert Date to LocalDate
        LocalDate birthday = currentClient.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        fx_bdate.setValue(birthday);
        // Set sex toggle
        if (currentClient.getSex().equals("M")) {
            fx_sex_m.selectedProperty().setValue(true);
        }
        else {
            fx_sex_m.selectedProperty().setValue(true);
        }
    }

    public void handleEditClientAction() {
        if (validateInput()) {
            int id = currentClient.getId();
            String name =  fx_name.getText();
            String surname = fx_surname.getText();
            String fiscalCode = fx_fiscal_code.getText();
            String phone = fx_phone.getText();
            String comment = fx_comment.getText();
            LocalDate ldBirthday = fx_bdate.getValue();
            // Convert LocalDate to Date
            Date birthday = Date.from(ldBirthday.atStartOfDay(ZoneId.systemDefault()).toInstant());
            RadioButton chk = (RadioButton) sex.getSelectedToggle();
            String clientSex = chk.getText();

            Client client = new Client(id, name, surname, clientSex, fiscalCode, birthday, phone, comment);

            // Update user only if there are any changes
            if(!client.equals(currentClient)) {
                TransferObject response = FrontController.handleRequest(UPDATE, CLIENT, client);

                if(response.getOperationResult()) {
                    closeWindow();
                    AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Cliente \u00E8 succesivamente aggiornato.");
                    // Update Users view
                    FrontController.handleRequest("Clients");
                }
            }
            else {
                closeWindow();
            }
        }
    }
}
