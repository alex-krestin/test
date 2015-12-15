package presentation.controller.clients;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import presentation.validation.GridValidator;

import java.time.LocalDate;

import static presentation.validation.GridValidator.ValidationFlag.ONE;
import static presentation.validation.ValidationConstraint.*;
import static presentation.validation.ValidationPattern.*;
import static utility.Constants.MIN_AGE;


public class ClientPopupController extends ClientMainController {
    public GridPane gridPane;
    public TextField fx_name;
    public TextField fx_surname;
    public RadioButton fx_sex_m;
    public RadioButton fx_sex_f;
    public CustomDatePicker fx_bday;
    public TextField fx_fiscal_code;
    public TextField fx_phone;
    public TextArea fx_comment;
    public ToggleGroup sex;

    protected final LocalDate fromDate = LocalDate.now().minusYears(MIN_AGE);

    @SuppressWarnings("LawOfDemeter")
    boolean validateInput() {
        GridValidator gv = new GridValidator(gridPane);
        gv.addConstraint(NOT_EMPTY).addPattern(NAME_PATTERN).validate(fx_name);
        gv.addConstraint(NOT_EMPTY).addPattern(NAME_PATTERN).validate(fx_surname);
        gv.addConstraint(SELECTED_TOGGLE).addFlag(ONE).validate(fx_sex_f, fx_sex_m);
        gv.addConstraint(NOT_NULL).addOption(IS_BEFORE, fromDate).validate(fx_bday);
        gv.addConstraint(NOT_EMPTY).addPattern(FISCAL_CODE_PATTERN).validate(fx_fiscal_code);
        gv.addConstraint(NOT_EMPTY).addPattern(INTEGER_PATTERN).validate(fx_phone);

        return gv.isValid();
    }

}
