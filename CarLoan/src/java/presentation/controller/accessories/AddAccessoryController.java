package presentation.controller.accessories;


import entity.Accessory;
import entity.Response;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.*;
import static presentation.helper.OperationCode.ADD;

public class AddAccessoryController extends AccessoryPopupController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_agency.loadValues(AGENCY);
        fx_category.loadValues(ACCESSORY_CATEGORY);
    }

    @SuppressWarnings("LawOfDemeter")
    public void handleSaveAccessoryAction() {

        if (validateInput()) {

            Accessory accessory = new Accessory.Builder()
                    .title(fx_title.getText())
                    .description(fx_description.getText())
                    .inventoryCode(fx_icode.getText())
                    .category(fx_category.getValue())
                    .currentAgency(fx_agency.getValue()).build();

            Response response = FrontController.handleRequest(ADD, ACCESSORY, accessory);

            if(response.getOperationResult()) {
                closeWindow();
                AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Acccessorio \u00E8 aggiunto correttamente.");
                // Update Users view
                FrontController.handleRequest("Accessories");
            }

        }
    }
}
