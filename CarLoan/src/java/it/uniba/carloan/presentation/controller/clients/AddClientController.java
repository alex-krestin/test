package it.uniba.carloan.presentation.controller.clients;

import it.uniba.carloan.entity.Client;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.FrontController;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.CLIENT;
import static it.uniba.carloan.presentation.helper.OperationCode.ADD;


public class AddClientController extends ClientPopupController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_bday.disableAfter(fromDate);
    }

    public void handleSaveClientAction() {
        if (validateInput()) {
            RadioButton chk = (RadioButton) sex.getSelectedToggle();
            String clientSex = chk.getText();

            @SuppressWarnings("LawOfDemeter")
            Client client = new Client.Builder()
                    .name(fx_name.getText())
                    .surname(fx_surname.getText())
                    .sex(clientSex)
                    .fiscalCode(fx_fiscal_code.getText())
                    .birthday(fx_bday.getValue())
                    .phoneNumber(fx_phone.getText())
                    .comment(fx_comment.getText()).build();

            Response response = FrontController.handleRequest(ADD, CLIENT, client);

            if(response.getOperationResult()) {
                closeWindow();
                AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Cliente \u00E8 aggiunto correttamente.");
                // Update Users view
                FrontController.handleRequest("Clients");
            }
        }
    }
}
