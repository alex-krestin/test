package presentation.controller.clients;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import presentation.controller.InputValidationController;
import utility.Constants;

import java.time.LocalDate;

import static presentation.controller.InputValidationController.FISCAL_CODE_PATTERN;
import static presentation.controller.InputValidationController.NAME_PATTERN;
import static presentation.controller.InputValidationController.PHONE_PATTERN;
import static utility.Constants.MIN_AGE;


public class ClientPopupController extends ClientMainController {
    public Label fx_name_label;
    public TextField fx_name;
    public ImageView fx_name_validation;
    public Label fx_surname_label;
    public TextField fx_surname;
    public ImageView fx_surname_validation;
    public Label fx_sex_label;
    public ToggleGroup sex;
    public RadioButton fx_sex_m;
    public RadioButton fx_sex_f;
    public ImageView fx_sex_validation;
    public Label fx_bdate_label;
    public DatePicker fx_bdate;
    public ImageView fx_bdate_validation;
    public Label fx_fiscal_code_label;
    public TextField fx_fiscal_code;
    public ImageView fx_fiscal_code_validation;
    public Label fx_phone_label;
    public TextField fx_phone;
    public ImageView fx_phone_validation;
    public TextArea fx_comment;
    public Label fx_error_message;

    LocalDate fromDate = LocalDate.now().minusYears(MIN_AGE);


    protected boolean validateInput() {
        // Reset error message
        fx_error_message.setText(null);

        InputValidationController validator = new InputValidationController();
        // Perform validations
        validator.validateField(NAME_PATTERN, fx_name, fx_name_validation, fx_name_label);
        validator.validateField(NAME_PATTERN, fx_surname, fx_surname_validation, fx_surname_label);
        validator.isSelected(sex, fx_sex_validation, fx_sex_label);
        LocalDate fromDate = LocalDate.now().minusYears(21);
        validator.validateDate(fromDate, null, fx_bdate, fx_bdate_validation, fx_bdate_label);
        validator.validateField(FISCAL_CODE_PATTERN, fx_fiscal_code, fx_fiscal_code_validation, fx_fiscal_code_label);
        validator.validateField(PHONE_PATTERN, fx_phone, fx_phone_validation, fx_phone_label);

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
