package presentation.validation.rules;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ObjectComboBox;
import javafx.scene.control.TimePicker;
import presentation.validation.ValidationObject;
import presentation.validation.ValidationRule;
import presentation.validation.exception.GenericValidationException;

import javax.activation.UnsupportedDataTypeException;


public class NotNullValidationRule implements ValidationRule {
    @Override
    public void validate(ValidationObject vo) throws GenericValidationException, UnsupportedDataTypeException {
        String msg = "Il campo " + vo.getObjectName() + " è obbligatorio.";

        Object object = vo.getMainObject();

        if (object instanceof DatePicker) {
            DatePicker dp = (DatePicker) object;

            if (dp.getValue() == null) {
                throw new GenericValidationException(msg);
            }
        }
        else if (object instanceof ObjectComboBox<?> || object instanceof TimePicker) {
            ComboBox<?> ocb = (ComboBox<?>) object;

            if (ocb.getValue() == null) {
                throw new GenericValidationException(msg);
            }
        }

        else throw new UnsupportedDataTypeException();
    }
}
