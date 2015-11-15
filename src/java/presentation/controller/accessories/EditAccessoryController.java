package presentation.controller.accessories;

import entity.Accessory;
import entity.Agency;
import entity.Category;
import entity.TransferObject;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.controller.accessories.AccessoryMainController;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.ACCESSORY;
import static presentation.helper.OperationCode.UPDATE;


public class EditAccessoryController extends AccessoryPopupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_title.setText(currentAccessory.getTitle());
        fx_description.setText(currentAccessory.getDescription());
        fx_icode.setText(currentAccessory.getInventoryCode());
        setCategoriesList();
        fx_category.getItems().remove(0);
        fx_category.getSelectionModel().select(currentAccessory.getCategory());
    }

    public void handleEditAccessoryAction() {
        if (validateInput()) {

            int id = currentAccessory.getId();
            String title =  fx_title.getText();
            String description = fx_description.getText();
            String inventoryCode = fx_icode.getText();
            Agency agency = currentAccessory.getAgency();
            Category category = fx_category.getValue();

            Accessory accessory = new Accessory(id, category, title, description, inventoryCode, agency);

            // Update user only if there are any changes
            if(!accessory.equals(currentAccessory)) {
                TransferObject response = FrontController.handleRequest(UPDATE, ACCESSORY, accessory);

                if(response.getOperationResult()) {
                    closeWindow();
                    AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Accessorio \u00E8 succesivamente aggiornato.");
                    // Update Users view
                    FrontController.handleRequest("Accessories");
                }
            }
            else {
                closeWindow();
            }
        }
    }
}
