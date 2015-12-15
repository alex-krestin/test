package presentation.validation.rules;


import javafx.scene.control.DatePicker;
import presentation.validation.ValidationObject;
import presentation.validation.ValidationRule;
import presentation.validation.exception.GenericValidationException;

import javax.activation.UnsupportedDataTypeException;
import java.time.LocalDate;

public class IsAfterValidationRule implements ValidationRule {
    @Override
    public void validate(ValidationObject vo) throws GenericValidationException, UnsupportedDataTypeException {
        Object object = vo.getMainObject();
        LocalDate date = null;

        if (vo.getOptionValue() instanceof LocalDate) {
            date = (LocalDate) vo.getOptionValue();
        }
        else if (vo.getOptionValue() instanceof DatePicker) {
            DatePicker dp = (DatePicker) vo.getOptionValue();
            date = dp.getValue();
        }

        if (object instanceof DatePicker) {
            DatePicker dp = (DatePicker) object;

            if (date != null && dp.getValue().isBefore(date)) {
                throw new GenericValidationException(vo.getObjectName() + " deve essere maggiore di " + date);
            }
        }

        else throw new UnsupportedDataTypeException();
    }



}
