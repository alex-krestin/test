package presentation.controller.agencies;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import presentation.controller.InputValidationController;

import static presentation.controller.InputValidationController.*;
import static presentation.controller.InputValidationController.EMAIL_PATTERN;
import static presentation.controller.InputValidationController.PHONE_PATTERN;


public class AgencyPopupController extends AgencyMainController {

    public Label fx_agency_code_label;
    public TextField fx_agency_code;
    public ImageView fx_agency_code_validation;
    public Label fx_city_label;
    public TextField fx_city;
    public ImageView fx_city_validation;
    public Label fx_address_label;
    public TextField fx_address;
    public ImageView fx_address_validation;
    public Label fx_phone_label;
    public TextField fx_phone;
    public ImageView fx_phone_validation;
    public Label fx_fax_label;
    public TextField fx_fax;
    public ImageView fx_fax_validation;
    public Label fx_email_label;
    public TextField fx_email;
    public ImageView fx_email_validation;
    public Label fx_error_message;

    protected boolean validateInput() {
        // Reset error message and errors array
        fx_error_message.setText(null);

        InputValidationController validator = new InputValidationController();
        // Perform validations
        validator.validateField(AGENCY_CODE_PATTERN, fx_agency_code, fx_agency_code_validation, fx_agency_code_label);
        validator.validateField(NAME_PATTERN, fx_city, fx_city_validation, fx_city_label);
        validator.validateField(ADDRESS_PATTERN, fx_address, fx_address_validation, fx_address_label);
        validator.validateOptionalField(PHONE_PATTERN, fx_phone, fx_phone_validation, fx_phone_label);
        validator.validateOptionalField(PHONE_PATTERN, fx_fax, fx_fax_validation, fx_fax_label);
        validator.validateOptionalField(EMAIL_PATTERN, fx_email, fx_email_validation, fx_email_label);

        // Show error message if any
        switch (validator.getNumErrors()) {
            case 0:
                return true;
            default:
                fx_error_message.setText(validator.getFirstError());
                return false;
        }
    }
}
