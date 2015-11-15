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
import static presentation.helper.OperationCode.ADD;


public class AddClientController extends ClientPopupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableDateSelection(fromDate, null, fx_bdate);
    }

    public void handleSaveClientAction() {
        if (validateInput()) {
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

            Client client = new Client(name, surname, clientSex, fiscalCode, birthday, phone, comment);

            TransferObject response = FrontController.handleRequest(ADD, CLIENT, client);

            if(response.getOperationResult()) {
                closeWindow();
                AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Cliente \u00E8 aggiunto correttamente.");
                // Update Users view
                FrontController.handleRequest("Clients");
            }
        }
    }


}
