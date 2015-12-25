package it.uniba.carloan.presentation.controller.penalties;


import it.uniba.carloan.entity.Category;
import it.uniba.carloan.javafx.scene.control.ObjectComboBox;
import it.uniba.carloan.presentation.validation.GridValidator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import static it.uniba.carloan.presentation.validation.ValidationConstraint.NOT_EMPTY;
import static it.uniba.carloan.presentation.validation.ValidationConstraint.NOT_NULL;

public class PenaltyPopupController extends PenaltyMainController {
    public TextField fx_title;
    public TextField fx_description;
    public GridPane gridPane;
    public ObjectComboBox<Category> fx_category;

    @SuppressWarnings("LawOfDemeter")
    boolean validateInput() {
        GridValidator gv = new GridValidator(gridPane);
        gv.addConstraint(NOT_EMPTY).validate(fx_title);
        gv.addConstraint(NOT_NULL).validate(fx_category);

        return gv.isValid();
    }
}
