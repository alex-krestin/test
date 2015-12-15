package presentation.validation.rules;


import javafx.scene.control.TextField;
import presentation.validation.ValidationObject;
import presentation.validation.ValidationRule;
import presentation.validation.exception.GenericValidationException;

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

