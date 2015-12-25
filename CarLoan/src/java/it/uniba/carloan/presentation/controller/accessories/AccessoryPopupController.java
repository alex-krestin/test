package it.uniba.carloan.presentation.controller.accessories;

import it.uniba.carloan.entity.Agency;
import it.uniba.carloan.entity.Category;
import it.uniba.carloan.javafx.scene.control.ObjectComboBox;
import it.uniba.carloan.presentation.validation.GridValidator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import static it.uniba.carloan.presentation.validation.ValidationConstraint.NOT_EMPTY;
import static it.uniba.carloan.presentation.validation.ValidationConstraint.NOT_NULL;
import static it.uniba.carloan.presentation.validation.ValidationPattern.ALPHANUMERIC_PATTERN;

public class AccessoryPopupController extends AccessoryMainController {
    public TextField fx_title;
    public TextField fx_icode;
    public TextField fx_description;
    public GridPane gridPane;
    public ObjectComboBox<Category> fx_category;
    public ObjectComboBox<Agency> fx_agency;

    @SuppressWarnings("LawOfDemeter")
    boolean validateInput() {
        GridValidator gv = new GridValidator(gridPane);
        gv.addConstraint(NOT_EMPTY).validate(fx_title);
        gv.addConstraint(NOT_EMPTY).addPattern(ALPHANUMERIC_PATTERN).validate(fx_icode);
        gv.addConstraint(NOT_NULL).validate(fx_category);
        gv.addConstraint(NOT_NULL).validate(fx_agency);
        return gv.isValid();
    }

}
