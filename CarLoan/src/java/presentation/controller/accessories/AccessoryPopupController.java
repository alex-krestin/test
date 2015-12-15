package presentation.controller.accessories;

import entity.Agency;
import entity.Category;
import javafx.scene.control.ObjectComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import presentation.validation.GridValidator;

import static presentation.validation.ValidationConstraint.NOT_EMPTY;
import static presentation.validation.ValidationConstraint.NOT_NULL;
import static presentation.validation.ValidationPattern.ALPHANUMERIC;

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
        gv.addConstraint(NOT_EMPTY).addPattern(ALPHANUMERIC).validate(fx_icode);
        gv.addConstraint(NOT_NULL).validate(fx_category);
        gv.addConstraint(NOT_NULL).validate(fx_agency);
        return gv.isValid();
    }

}
