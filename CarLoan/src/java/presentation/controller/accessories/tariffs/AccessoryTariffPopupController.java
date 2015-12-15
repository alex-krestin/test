package presentation.controller.accessories.tariffs;


import entity.AccessoryTariff;
import entity.Category;
import javafx.scene.control.CustomDatePicker;
import javafx.scene.control.ObjectComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import presentation.validation.GridValidator;

import java.math.BigDecimal;
import java.time.LocalDate;

import static presentation.validation.ValidationConstraint.*;

public class AccessoryTariffPopupController extends AccessoryTariffsMainController {
    public ObjectComboBox<Category> fx_category;
    public TextField fx_daily_price;
    public TextField fx_maxDays;
    public CustomDatePicker fx_fromDate;
    public CustomDatePicker fx_toDate;
    public GridPane gridPane;


    protected void addFieldsRestriction() {
        addPriceFieldListener(fx_daily_price);
        fx_fromDate.disableBefore(LocalDate.now());

        fx_fromDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fx_toDate.disableBefore(fx_fromDate.getValue());
            }
            // Enable toDate after fromDate selection
            if(fx_toDate.isDisabled()) {
                fx_toDate.disableProperty().set(false);
            }
        });

        fx_toDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.isBefore(fx_fromDate.getValue())) {
                fx_toDate.setValue(null);
            }
        });
    }

    @SuppressWarnings("LawOfDemeter")
    protected boolean validateInput() {
        GridValidator gv = new GridValidator(gridPane);
        gv.addConstraint(NOT_EMPTY).validate(fx_daily_price);
        gv.addConstraint(NOT_EMPTY).validate(fx_maxDays);
        gv.addConstraint(NOT_NULL).validate(fx_category);
        gv.addConstraint(NOT_NULL).validate(fx_fromDate);
        if (gv.addConstraint(NOT_NULL).showError(false).validate(fx_toDate)) {
            gv.addOption(IS_AFTER, fx_fromDate).validate(fx_toDate);
        }

        //TODO
        //validator.validateDate(null, null, fx_fromDate, fx_fromDate_validation, fx_fromDate_label);

        return gv.isValid();
    }

    @SuppressWarnings("LawOfDemeter")
    protected AccessoryTariff getInputData() {
        BigDecimal dailyPrice = new BigDecimal(Float.parseFloat(fx_daily_price.getText()));
        int maxDays = Integer.parseInt(fx_maxDays.getText());

        return new AccessoryTariff.Builder()
                .dailyPrice(dailyPrice)
                .maxDays(maxDays)
                .category(fx_category.getValue())
                .fromDate(fx_fromDate.getValue())
                .toDate(fx_toDate.getValue()).build();
    }
}
