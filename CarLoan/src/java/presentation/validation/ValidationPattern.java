package presentation.validation;

public enum ValidationPattern {
    INTEGER_PATTERN ("^[0-9]+$"),
    ALPHANUMERIC ("[a-zA-Z0-9]+$"),
    NAME_PATTERN ("^[\\p{L}\\s'.-]+$"),
    ADDRESS_PATTERN ("^[0-9\\p{L}\\s'.,-]+$"),
    FISCAL_CODE_PATTERN ("[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d\\d[a-zA-Z]"),
    USERNAME_PATTERN ("^[a-zA-Z0-9][a-zA-Z0-9_.-]+$"),
    PLATE_PATTERN ("[a-zA-Z]{2}\\d{3,4}[a-zA-Z]{2}$"),
    STRONG_PASSWORD_PATTERN ("(?-i)(?=^.{8,}$)((?!.*\\s)(?=.*[A-Z])(?=.*[a-z]))" +
            "((?=(.*\\d){1,})|(?=(.*\\W){1,}))^.*$"),
    EMAIL_PATTERN ("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)" +
            "*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-" +
            "\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:" +
            "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]" +
            "*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c" +
            "\\x0e-\\x7f])+)\\])");

    private final String text;

    ValidationPattern(final String text) {
        this.text = text;
    }

    public String getPattern() {
        return text;
    }
}
