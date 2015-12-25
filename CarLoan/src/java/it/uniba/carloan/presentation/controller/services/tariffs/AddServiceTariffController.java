package it.uniba.carloan.presentation.controller.services.tariffs;


import it.uniba.carloan.entity.ServiceTariff;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.SERVICE;
import static it.uniba.carloan.presentation.helper.EntityCode.SERVICE_TARIFF;

public class AddServiceTariffController extends ServiceTariffPopupController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_service.loadValues(SERVICE);
        if (currentTariff != null) {
            fx_service.setValue(currentTariff.getService());
        }
        addFieldsRestriction();
    }

    public void handleSaveTariffAction() {
        if (validateInput()) {
            ServiceTariff tariff = getInputData();
            executeInsert(SERVICE_TARIFF, tariff);
        }
    }

}
