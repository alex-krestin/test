package presentation.validation;


import presentation.validation.rules.*;

public enum  ValidationConstraint {
    NOT_EMPTY (new NotEmptyValidationRule()),
    NOT_NULL (new NotNullValidationRule()),
    SELECTED_TOGGLE (new ToggleSelectionValidationRule()),
    CHECKED (new CheckedValidationRule()),
    MAX_LENGHT (new MaxLenghtValidationRule()),
    MAX_VALUE (new MaxValueValidationRule()),
    MIN_VALUE (new MinValueValidationRule()),
    IS_AFTER (new IsAfterValidationRule()),
    IS_BEFORE (new IsBeforeValidationRule()),
    EQUAL (new EqualValidationRule());


    private final ValidationRule rule;

    ValidationConstraint(ValidationRule validationRule) {
        rule = validationRule;
    }

    ValidationRule getValidationRule() {
        return rule;
    }
}
