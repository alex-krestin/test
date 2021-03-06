package it.uniba.carloan.presentation.validation.rules;

import it.uniba.carloan.presentation.validation.ValidationObject;
import it.uniba.carloan.presentation.validation.ValidationRule;
import it.uniba.carloan.presentation.validation.exception.GenericValidationException;
import javafx.scene.control.Toggle;

import javax.activation.UnsupportedDataTypeException;


public class ToggleSelectionValidationRule implements ValidationRule {
    @Override
    public void validate(ValidationObject vo) throws GenericValidationException, UnsupportedDataTypeException {
       // if (vo.getMainObject() == null) throw new NullPointerException(); //todo
        if (!(vo.getMainObject() instanceof Toggle)) throw new UnsupportedDataTypeException();

        Toggle tg = (Toggle) vo.getMainObject();

        if (!tg.isSelected()) throw new GenericValidationException("Il campo " +
                vo.getObjectName() + " deve essere selezionato.");
    }
}
