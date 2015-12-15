package presentation.controller.cars.tariffs;

import entity.CarTariff;
import entity.Category;
import javafx.scene.control.CustomDatePicker;
import javafx.scene.control.ObjectComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import presentation.validation.GridValidator;

import java.math.BigDecimal;
import java.time.LocalDate;

import static presentation.validation.ValidationConstraint.NOT_EMPTY;


public class CarTariffPopupController extends CarTariffsMainController {
    public ObjectComboBox<Category> fx_category;
    public TextField fx_daily_price;
    public TextField fx_weekly_price;
    public TextField fx_kmPrice;
    public TextField fx_freeKmPrice;
    public CustomDatePicker fx_fromDate;
    public CustomDatePicker fx_toDate;
    public GridPane gridPane;


    protected void addFieldsRestriction() {
        addPriceFieldListener(fx_daily_price, fx_weekly_price, fx_kmPrice, fx_freeKmPrice);
        fx_fromDate.disableBefore(LocalDate.now());

        fx_fromDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fx_toDate.disableBefore(fx_fromDate.getValue());

                if (fx_toDate.getValue() != null && newValue.isAfter(fx_toDate.getValue())) {
                    fx_toDate.setValue(null);
                }
            }
            // Enable toDate after fromDate selection
            if(fx_toDate.isDisabled()) {
                fx_toDate.disableProperty().set(false);
            }
        });
    }

    @SuppressWarnings("LawOfDemeter")
    protected boolean validateInput() {
        GridValidator gv = new GridValidator(gridPane);
        gv.addConstraint(NOT_EMPTY).validate(fx_daily_price);
        gv.addConstraint(NOT_EMPTY).validate(fx_weekly_price);
        gv.addConstraint(NOT_EMPTY).validate(fx_kmPrice);
        gv.addConstraint(NOT_EMPTY).validate(fx_freeKmPrice);

        // TODO
        //validator.validateDate(null, null, fx_fromDate, fx_fromDate_validation, fx_fromDate_label);

        return gv.isValid();
    }

    @SuppressWarnings("LawOfDemeter")
    protected CarTariff getInputData() {
        BigDecimal dailyPrice = new BigDecimal(Float.parseFloat(fx_daily_price.getText()));
        BigDecimal weeklyPrice = new BigDecimal(Float.parseFloat(fx_weekly_price.getText()));
        BigDecimal kmPrice = new BigDecimal(Float.parseFloat(fx_kmPrice.getText()));
        BigDecimal freeKmPrice = new BigDecimal(Float.parseFloat(fx_freeKmPrice.getText()));

        return new CarTariff.Builder()
                .category(fx_category.getValue())
                .dailyPrice(dailyPrice)
                .weeklyPrice(weeklyPrice)
                .mileagePrice(kmPrice)
                .freeMileagePrice(freeKmPrice)
                .fromDate(fx_fromDate.getValue())
                .toDate(fx_toDate.getValue()).build();
    }

}
