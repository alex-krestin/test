package it.uniba.carloan.presentation.controller.accessories;

import it.uniba.carloan.entity.Accessory;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.ACCESSORY;
import static it.uniba.carloan.presentation.helper.EntityCode.ACCESSORY_CATEGORY;


public class EditAccessoryController extends AccessoryPopupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_title.setText(currentAccessory.getTitle());
        fx_description.setText(currentAccessory.getDescription());
        fx_icode.setText(currentAccessory.getInventoryCode());
        fx_category.loadValues(ACCESSORY_CATEGORY);
        fx_category.getSelectionModel().select(currentAccessory.getCategory());
    }

    @SuppressWarnings("LawOfDemeter")
    public void handleEditAccessoryAction() {
        if (validateInput()) {

            Accessory accessory = new Accessory.Builder()
                    .id(currentAccessory.getId())
                    .title(fx_title.getText())
                    .description(fx_description.getText())
                    .inventoryCode(fx_icode.getText())
                    .category(fx_category.getValue()).build();

            executeUpdate(currentAccessory, accessory, ACCESSORY);
        }
    }
}
