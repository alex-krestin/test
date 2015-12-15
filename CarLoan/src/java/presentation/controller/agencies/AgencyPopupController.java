package presentation.controller.agencies;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import presentation.validation.GridValidator;

import static presentation.validation.ValidationConstraint.MAX_LENGHT;
import static presentation.validation.ValidationConstraint.NOT_EMPTY;
import static presentation.validation.ValidationPattern.*;


public class AgencyPopupController extends AgencyMainController {

    public TextField fx_agency_code;
    public TextField fx_city;
    public TextField fx_address;
    public TextField fx_phone;
    public TextField fx_fax;
    public TextField fx_email;
    public GridPane gridPane;

    @SuppressWarnings("LawOfDemeter")
    boolean validateInput() {
        GridValidator gv = new GridValidator(gridPane);
        gv.addConstraint(NOT_EMPTY).addPattern(ALPHANUMERIC)
                .addOption(MAX_LENGHT, 5).validate(fx_agency_code);
        gv.addConstraint(NOT_EMPTY).addPattern(NAME_PATTERN).validate(fx_city);
        gv.addConstraint(NOT_EMPTY).addPattern(ADDRESS_PATTERN).validate(fx_address);
        gv.addPattern(EMAIL_PATTERN).validate(fx_email);
        gv.addPattern(INTEGER_PATTERN).validate(fx_phone);
        gv.addPattern(INTEGER_PATTERN).validate(fx_fax);

        return gv.isValid();
    }
}
