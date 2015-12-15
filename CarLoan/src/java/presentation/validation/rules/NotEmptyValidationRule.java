package presentation.validation.rules;


import javafx.scene.control.TextField;
import presentation.validation.ValidationObject;
import presentation.validation.ValidationRule;
import presentation.validation.exception.GenericValidationException;

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
