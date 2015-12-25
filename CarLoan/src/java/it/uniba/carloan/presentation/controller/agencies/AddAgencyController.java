package it.uniba.carloan.presentation.controller.agencies;

import it.uniba.carloan.entity.Agency;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.FrontController;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.AGENCY;
import static it.uniba.carloan.presentation.helper.OperationCode.ADD;


public class AddAgencyController extends AgencyPopupController implements Initializable {
    @SuppressWarnings("LawOfDemeter")
    public void handleSaveAgencyAction() {
        if (validateInput()) {
            String agencyCode = fx_agency_code.getText();
            String city = fx_city.getText();
            String address = fx_address.getText();
            String phone = fx_phone.getText();
            String fax = fx_fax.getText();
            String email = fx_email.getText();

            Agency agency = new Agency.Builder()
                    .agencyCode(agencyCode)
                    .city(city)
                    .address(address)
                    .phoneNumber(phone)
                    .faxNumber(fax)
                    .email(email).build();

            Response response = FrontController.handleRequest(ADD, AGENCY, agency);

            if(response.getOperationResult()) {
                closeWindow();
                AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Agenzia \u00E8 aggiunta correttamente.");
                // Update Agencies view
                FrontController.handleRequest("Agencies");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
