package presentation.validation.rules;


import javafx.scene.control.CheckBox;
import presentation.validation.ValidationObject;
import presentation.validation.ValidationRule;
import presentation.validation.exception.GenericValidationException;

import javax.activation.UnsupportedDataTypeException;

public class CheckedValidationRule implements ValidationRule {
    @Override
    public void validate(ValidationObject vo) throws GenericValidationException, UnsupportedDataTypeException {
       // if (vo.getMainObject() == null) throw new NullPointerException(); //todo
        if (!(vo.getMainObject() instanceof CheckBox)) throw new UnsupportedDataTypeException();

        CheckBox checkBox = (CheckBox) vo.getMainObject();

        if (!checkBox.isSelected()) throw new GenericValidationException("Il campo " +
                vo.getObjectName() + " deve essere selezionato.");
    }
}
