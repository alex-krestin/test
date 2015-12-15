package presentation.validation.rules;


import javafx.scene.control.TextField;
import presentation.validation.ValidationObject;
import presentation.validation.ValidationRule;
import presentation.validation.exception.GenericValidationException;

import javax.activation.UnsupportedDataTypeException;

public class EqualValidationRule implements ValidationRule {
    @Override
    public void validate(ValidationObject vo) throws GenericValidationException, UnsupportedDataTypeException {
        Object object = vo.getMainObject();
        Object optionValue = vo.getOptionValue();

        if (object instanceof TextField && optionValue instanceof TextField) {
            TextField tf = (TextField) object;
            TextField otf = (TextField) optionValue;

            if(!tf.getText().equals(otf.getText())) {
                throw new GenericValidationException("Il campo " + vo.getObjectName() + " non è valido.");
            }
        }

        else throw new UnsupportedDataTypeException();
    }
}
