package it.uniba.carloan.presentation.controller.cars.tariffs;


import it.uniba.carloan.entity.CarTariff;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.CAR_CATEGORY;
import static it.uniba.carloan.presentation.helper.EntityCode.CAR_TARIFF;

public class AddCarTariffController extends CarTariffPopupController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_category.loadValues(CAR_CATEGORY);
        if (currentTariff != null) {
            fx_category.setValue(currentTariff.getCategory());
        }
        addFieldsRestriction();
    }


    public void handleSaveTariffAction() {
        if (validateInput()) {
            CarTariff tariff = getInputData();
            executeInsert(CAR_TARIFF, tariff);
        }
    }
}
