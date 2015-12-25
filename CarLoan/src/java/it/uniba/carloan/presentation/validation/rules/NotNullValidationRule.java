package it.uniba.carloan.presentation.validation.rules;

import it.uniba.carloan.javafx.scene.control.ObjectComboBox;
import it.uniba.carloan.javafx.scene.control.TimePicker;
import it.uniba.carloan.presentation.validation.ValidationObject;
import it.uniba.carloan.presentation.validation.ValidationRule;
import it.uniba.carloan.presentation.validation.exception.GenericValidationException;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

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
