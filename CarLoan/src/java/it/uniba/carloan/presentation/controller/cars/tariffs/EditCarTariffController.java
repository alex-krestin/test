package it.uniba.carloan.presentation.controller.cars.tariffs;


import it.uniba.carloan.entity.CarTariff;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.CAR_CATEGORY;
import static it.uniba.carloan.presentation.helper.EntityCode.CAR_TARIFF;

public class EditCarTariffController extends CarTariffPopupController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_category.loadValues(CAR_CATEGORY);
        fx_category.setValue(currentTariff.getCategory());
        fx_daily_price.setText(currentTariff.getDailyPrice().toString());
        fx_weekly_price.setText(currentTariff.getWeeklyPrice().toString());
        fx_kmPrice.setText(currentTariff.getMileagePrice().toString());
        fx_freeKmPrice.setText(currentTariff.getFreeMileagePrice().toString());
        fx_fromDate.setValue(currentTariff.getFromDate());
        fx_toDate.setValue(currentTariff.getToDate());
        addFieldsRestriction();
    }


    public void handleEditTariffAction() {
        if (validateInput()) {
            CarTariff tariff = getInputData();
            executeUpdate(currentTariff, tariff, CAR_TARIFF);
        }
    }
}
