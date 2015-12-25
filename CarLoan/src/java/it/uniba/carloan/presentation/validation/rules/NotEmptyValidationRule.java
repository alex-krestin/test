package it.uniba.carloan.presentation.validation.rules;


import it.uniba.carloan.presentation.validation.ValidationObject;
import it.uniba.carloan.presentation.validation.ValidationRule;
import it.uniba.carloan.presentation.validation.exception.GenericValidationException;
import javafx.scene.control.TextField;

import javax.activation.UnsupportedDataTypeException;

public class NotEmptyValidationRule implements ValidationRule {
    @Override
    public void validate(ValidationObject vo) throws GenericValidationException, UnsupportedDataTypeException {
        String msg = "Il campo " + vo.getObjectName() + " è obbligatorio.";

        Object object = vo.getMainObject();

        if (object instanceof TextField) {
            TextField tf = (TextField) object;

            if(tf.getText().trim().equals("")) {
                throw new GenericValidationException(msg);
            }
        }
        else throw new UnsupportedDataTypeException();
    }
}
