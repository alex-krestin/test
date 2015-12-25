package it.uniba.carloan.presentation.validation.rules;


import it.uniba.carloan.presentation.validation.ValidationObject;
import it.uniba.carloan.presentation.validation.ValidationRule;
import it.uniba.carloan.presentation.validation.exception.GenericValidationException;
import javafx.scene.control.TextField;

public class RegexValidationRule implements ValidationRule {
    @Override
    public void validate(ValidationObject vo) throws GenericValidationException {

        String pattern = vo.getRegexPattern();

        TextField textField = (TextField) vo.getMainObject();
        String txt = textField.getText().trim();

        if (!txt.matches(pattern)) {
            throw new GenericValidationException("Il campo " +
                    vo.getObjectName() + " non è valido.");
        }
    }
}

