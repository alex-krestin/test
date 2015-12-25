package it.uniba.carloan.presentation.controller.accessories.tariffs;


import it.uniba.carloan.entity.AccessoryTariff;
import it.uniba.carloan.presentation.helper.EntityCode;

import java.net.URL;
import java.util.ResourceBundle;

public class EditAccessoryTariffController extends AccessoryTariffPopupController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_category.loadValues(EntityCode.ACCESSORY_CATEGORY);
        fx_category.setValue(currentTariff.getCategory());
        fx_daily_price.setText(currentTariff.getDailyPrice().toString());
        fx_maxDays.setText(String.valueOf(currentTariff.getMaxDays()));
        fx_fromDate.setValue(currentTariff.getFromDate());
        fx_toDate.setValue(currentTariff.getToDate());
        addFieldsRestriction();
    }


    public void handleEditTariffAction() {
        if (validateInput()) {
            AccessoryTariff tariff = getInputData();
            executeUpdate(currentTariff, tariff, EntityCode.ACCESSORY_TARIFF);
        }
    }
}
