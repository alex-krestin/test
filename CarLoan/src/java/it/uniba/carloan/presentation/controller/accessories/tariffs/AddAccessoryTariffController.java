package it.uniba.carloan.presentation.controller.accessories.tariffs;


import it.uniba.carloan.entity.AccessoryTariff;
import it.uniba.carloan.presentation.helper.EntityCode;

import java.net.URL;
import java.util.ResourceBundle;

public class AddAccessoryTariffController extends AccessoryTariffPopupController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_category.loadValues(EntityCode.ACCESSORY_CATEGORY);
        if (currentTariff != null) {
            fx_category.setValue(currentTariff.getCategory());
        }
        addFieldsRestriction();
    }

    public void handleSaveTariffAction() {
        if (validateInput()) {
            AccessoryTariff tariff = getInputData();
            executeInsert(EntityCode.ACCESSORY_TARIFF, tariff);
        }
    }

}
