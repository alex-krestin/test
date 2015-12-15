package presentation.controller.clients;

import entity.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.CLIENT;


public class EditClientController extends ClientPopupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_bday.disableAfter(fromDate);

        fx_name.setText(currentClient.getName());
        fx_surname.setText(currentClient.getSurname());
        fx_fiscal_code.setText(currentClient.getFiscalCode());
        fx_phone.setText(currentClient.getPhoneNumber());
        fx_comment.setText(currentClient.getComment());
        fx_bday.setValue(currentClient.getBirthday());
        // Set sex toggle
        if (currentClient.getSex().equals("M")) {
            fx_sex_m.selectedProperty().setValue(true);
        }
        else {
            fx_sex_m.selectedProperty().setValue(true);
        }
    }

    @SuppressWarnings("LawOfDemeter")
    public void handleEditClientAction() {
        if (validateInput()) {
            // TODO refactor sex
            RadioButton chk = (RadioButton) sex.getSelectedToggle();
            String clientSex = chk.getText();

            Client client = new Client.Builder()
                    .id(currentClient.getId())
                    .name(fx_name.getText())
                    .surname(fx_surname.getText())
                    .sex(clientSex)
                    .fiscalCode(fx_fiscal_code.getText())
                    .birthday(fx_bday.getValue())
                    .phoneNumber(fx_phone.getText())
                    .comment(fx_comment.getText()).build();

            executeUpdate(currentClient, client, CLIENT);
        }
    }
}
