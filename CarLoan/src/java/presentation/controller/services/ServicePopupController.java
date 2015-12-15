package presentation.controller.services;


import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import presentation.validation.GridValidator;

import static presentation.validation.ValidationConstraint.NOT_EMPTY;

public class ServicePopupController extends ServiceMainController {
    public TextField fx_title;
    public TextField fx_description;
    public GridPane gridPane;

    @SuppressWarnings("LawOfDemeter")
    boolean validateInput() {
        GridValidator gv = new GridValidator(gridPane);
        gv.addConstraint(NOT_EMPTY).validate(fx_title);

        return gv.isValid();
    }
}
