package presentation.controller.agencies;

import entity.Agency;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.AGENCY;


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

    @SuppressWarnings("LawOfDemeter")
    public void handleEditAgencyAction() {
        if (validateInput()) {

            Agency agency = new Agency.Builder()
                    .id(currentAgency.getId())
                    .agencyCode(fx_agency_code.getText())
                    .city(fx_city.getText())
                    .address(fx_address.getText())
                    .phoneNumber(fx_phone.getText())
                    .faxNumber(fx_fax.getText())
                    .email(fx_email.getText()).build();

            executeUpdate(currentAgency, agency, AGENCY);
        }
    }
}
