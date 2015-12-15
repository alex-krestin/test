package presentation.controller.agencies;

import entity.Agency;
import entity.Response;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.AGENCY;
import static presentation.helper.OperationCode.ADD;


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
