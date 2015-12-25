package it.uniba.carloan.presentation.controller.services.tariffs;


import it.uniba.carloan.entity.ServiceTariff;
import it.uniba.carloan.presentation.helper.EntityCode;

import java.net.URL;
import java.util.ResourceBundle;

public class EditServiceTariffController extends ServiceTariffPopupController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_service.loadValues(EntityCode.SERVICE);
        fx_service.setValue(currentTariff.getService());
        fx_price.setText(currentTariff.getPrice().toString());
        fx_fromDate.setValue(currentTariff.getFromDate());
        fx_toDate.setValue(currentTariff.getToDate());
        addFieldsRestriction();
    }


    public void handleEditTariffAction() {
        if (validateInput()) {
            ServiceTariff tariff = getInputData();
            executeUpdate(currentTariff, tariff, EntityCode.SERVICE_TARIFF);
        }
    }
}
