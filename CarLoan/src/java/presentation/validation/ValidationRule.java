package presentation.validation;


import presentation.validation.exception.GenericValidationException;

import javax.activation.UnsupportedDataTypeException;

public interface ValidationRule {
    void validate(ValidationObject vo) throws GenericValidationException, UnsupportedDataTypeException;
}
