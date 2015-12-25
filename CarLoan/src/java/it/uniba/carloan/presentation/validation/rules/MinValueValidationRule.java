package it.uniba.carloan.presentation.validation.rules;

import it.uniba.carloan.presentation.validation.ValidationObject;
import it.uniba.carloan.presentation.validation.ValidationRule;
import it.uniba.carloan.presentation.validation.exception.GenericValidationException;
import javafx.scene.control.TextField;

import javax.activation.UnsupportedDataTypeException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MinValueValidationRule implements ValidationRule {
    private static final Logger log = Logger.getLogger(MinValueValidationRule.class.getName());

    @Override
    public void validate(ValidationObject vo) throws GenericValidationException, UnsupportedDataTypeException {
        Object object = vo.getMainObject();
        Integer minValue = null;

        try {
            minValue = (Integer) vo.getOptionValue();
        } catch (Exception e) {
            log.log(Level.WARNING, "Can't convert Object to an Integer: ", e);
            throw new UnsupportedDataTypeException();
        }

        if (object instanceof TextField) {
            TextField tf = (TextField) object;

            if (minValue != null && Integer.valueOf(tf.getText()) < minValue) {
                throw new GenericValidationException(vo.getObjectName() +
                        " non può essere minore di " + minValue + ".");
            }
        } else throw new UnsupportedDataTypeException();
    }
}
