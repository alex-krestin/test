package presentation.controller.penalties;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import presentation.controller.InputValidationController;

public class PenaltyPopupController extends PenaltyMainController {
    public Label fx_title_label;
    public TextField fx_title;
    public ImageView fx_title_validation;
    public TextField fx_description;
    public Label fx_error_message;

    protected boolean validateInput() {
        // Reset error message
        fx_error_message.setText(null);

        InputValidationController validator = new InputValidationController();
        // Perform validations
        validator.isEmpty(fx_title, fx_title_validation, fx_title_label);

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
