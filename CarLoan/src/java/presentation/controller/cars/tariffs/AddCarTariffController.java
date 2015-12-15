package presentation.controller.cars.tariffs;


import entity.CarTariff;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.CAR_CATEGORY;
import static presentation.helper.EntityCode.CAR_TARIFF;

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
