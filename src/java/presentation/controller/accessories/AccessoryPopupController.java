package presentation.controller.accessories;

import entity.Agency;
import entity.TransferObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.controller.InputValidationController;
import presentation.helper.exception.DataLoadingException;

import java.util.ArrayList;

import static presentation.controller.InputValidationController.ALPHANUMERIC;
import static presentation.helper.EntityCode.AGENCY;
import static presentation.helper.OperationCode.GET_ALL;

public class AccessoryPopupController extends AccessoryMainController {
    public Label fx_title_label;
    public TextField fx_title;
    public ImageView fx_title_validation;
    public Label fx_icode_label;
    public TextField fx_icode;
    public ImageView fx_icode_validation;
    public TextField fx_description;
    public Label fx_error_message;


    protected boolean validateInput() {
        // Reset error message
        fx_error_message.setText(null);

        InputValidationController validator = new InputValidationController();
        // Perform validations
        validator.isEmpty(fx_title, fx_title_validation, fx_title_label);
        validator.validateField(ALPHANUMERIC, fx_icode, fx_icode_validation, fx_icode_label);

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
