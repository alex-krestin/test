package it.uniba.carloan.presentation.controller.services.tariffs;


import it.uniba.carloan.entity.Service;
import it.uniba.carloan.entity.ServiceTariff;
import it.uniba.carloan.javafx.scene.control.CustomDatePicker;
import it.uniba.carloan.javafx.scene.control.ObjectComboBox;
import it.uniba.carloan.presentation.validation.GridValidator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.math.BigDecimal;
import java.time.LocalDate;

import static it.uniba.carloan.presentation.validation.ValidationConstraint.NOT_EMPTY;

public class ServiceTariffPopupController extends ServiceTariffsMainController {
    public ObjectComboBox<Service> fx_service;
    public TextField fx_price;
    public CustomDatePicker fx_fromDate;
    public CustomDatePicker fx_toDate;
    public GridPane gridPane;


    protected void addFieldsRestriction() {
         addPriceFieldListener(fx_price);
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
        gv.addConstraint(NOT_EMPTY).validate(fx_price);
        //TODO
        //validator.validateDate(null, null, fx_fromDate, fx_fromDate_validation, fx_fromDate_label);

        return gv.isValid();
    }

    @SuppressWarnings("LawOfDemeter")
    protected final ServiceTariff getInputData() {
        BigDecimal price = new BigDecimal(Float.parseFloat(fx_price.getText()));

        return new ServiceTariff.Builder()
                .service(fx_service.getValue())
                .price(price)
                .fromDate(fx_fromDate.getValue())
                .toDate(fx_toDate.getValue()).build();
    }
}
