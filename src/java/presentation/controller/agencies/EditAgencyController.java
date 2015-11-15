package presentation.controller.agencies;

import entity.Agency;
import entity.TransferObject;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.AGENCY;
import static presentation.helper.OperationCode.UPDATE;


public class EditAgencyController extends AgencyPopupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_agency_code.setText(currentAgency.getAgencyCode());
        fx_city.setText(currentAgency.getCity());
        fx_address.setText(currentAgency.getAddress());
        fx_phone.setText(currentAgency.getPhoneNumber());
        fx_fax.setText(currentAgency.getFaxNumber());
        fx_email.setText(currentAgency.getEmail());
    }

    public void handleEditAgencyAction() {
        if (validateInput()) {
            String agencyCode = fx_agency_code.getText();
            String city = fx_city.getText();
            String address = fx_address.getText();
            String phone = fx_phone.getText();
            String fax = fx_fax.getText();
            String email = fx_email.getText();

            Agency agency = new Agency(currentAgency.getAgencyID(), agencyCode, city, address, phone, fax, email);

            // Update agency only if there are any changes
            if(!agency.equals(currentAgency)) {
                TransferObject response = FrontController.handleRequest(UPDATE, AGENCY, agency);

                if(response.getOperationResult()) {
                    closeWindow();
                    AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Agenzia \u00E8 succesivamente aggiornata.");
                    // Update Agencies view
                    FrontController.handleRequest("Agencies");
                }
            }
            else {
                closeWindow();
            }
        }
    }
}
