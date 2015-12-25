package it.uniba.carloan.presentation.controller.cars;


import it.uniba.carloan.entity.Agency;
import it.uniba.carloan.javafx.scene.control.CustomTextField;
import it.uniba.carloan.javafx.scene.control.ObjectComboBox;
import it.uniba.carloan.presentation.validation.GridValidator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

import static it.uniba.carloan.presentation.validation.ValidationConstraint.*;
import static it.uniba.carloan.presentation.validation.ValidationPattern.INTEGER_PATTERN;
import static it.uniba.carloan.presentation.validation.ValidationPattern.PLATE_PATTERN;

public class CarPopupController extends CarMainController {

    public TextField fx_brand;
    public TextField fx_model;
    public CustomTextField fx_year;
    public TextField fx_plate;
    public CustomTextField fx_passengers;
    public CustomTextField fx_doors;
    public CustomTextField fx_mileage;
    public ObjectComboBox<Agency> fx_agency;
    public TextArea fx_description;
    public GridPane gridPane;


    protected void addFieldsRestriction() {
        fx_year.setMaxLength(4);
        fx_passengers.setMaxLength(2);
        fx_doors.setMaxLength(1);
        fx_mileage.setMaxLength(7);

        fx_year.limitToNumbers();
        fx_passengers.limitToNumbers();
        fx_doors.limitToNumbers();
        fx_mileage.limitToNumbers();
    }

    @SuppressWarnings("LawOfDemeter")
    protected boolean validateInput() {
        GridValidator gv = new GridValidator(gridPane);
        gv.addConstraint(NOT_EMPTY).validate(fx_brand);
        gv.addConstraint(NOT_EMPTY).validate(fx_model);
        int currentYear = LocalDate.now().getYear();
        gv.addConstraint(NOT_EMPTY).addOption(MAX_VALUE, currentYear)
                .addOption(MIN_VALUE, 1950).validate(fx_year);
        gv.addConstraint(NOT_EMPTY)
                .addPattern(PLATE_PATTERN).validate(fx_plate);
        gv.addConstraint(NOT_EMPTY)
                .addPattern(INTEGER_PATTERN).validate(fx_passengers);
        gv.addConstraint(NOT_EMPTY)
                .addPattern(INTEGER_PATTERN).validate(fx_doors);
        gv.addConstraint(NOT_EMPTY)
                .addPattern(INTEGER_PATTERN).validate(fx_mileage);
        gv.addConstraint(NOT_NULL).validate(fx_category);
        gv.addConstraint(NOT_NULL).validate(fx_agency);

        return gv.isValid();
    }

}
