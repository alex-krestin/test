package presentation.validation.rules;

import javafx.scene.control.TextField;
import presentation.validation.ValidationObject;
import presentation.validation.ValidationRule;
import presentation.validation.exception.GenericValidationException;

import javax.activation.UnsupportedDataTypeException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MaxValueValidationRule implements ValidationRule {
    private static final Logger log = Logger.getLogger(MaxValueValidationRule.class.getName());

    @Override
    public void validate(ValidationObject vo) throws GenericValidationException, UnsupportedDataTypeException {
        Object object = vo.getMainObject();
        Integer maxValue = null;

        try {
            maxValue = (Integer) vo.getOptionValue();
        } catch (Exception e) {
            log.log(Level.WARNING, "Can't convert Object to an Integer: ", e);
            throw new UnsupportedDataTypeException();
        }

        if (object instanceof TextField) {
            TextField tf = (TextField) object;

            if (maxValue != null && Integer.valueOf(tf.getText()) > maxValue) {
                throw new GenericValidationException(vo.getObjectName() +
                        " non può essere maggiore di " + maxValue + ".");
            }
        } else throw new UnsupportedDataTypeException();
    }
}
