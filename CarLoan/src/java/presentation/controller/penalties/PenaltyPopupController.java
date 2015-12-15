package presentation.controller.penalties;


import entity.Category;
import javafx.scene.control.ObjectComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import presentation.validation.GridValidator;

import static presentation.validation.ValidationConstraint.NOT_EMPTY;
import static presentation.validation.ValidationConstraint.NOT_NULL;

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
