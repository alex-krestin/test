package presentation.controller;

public class InputValidationController {

    /*
    private final List<String> errors = new ArrayList<>();

    public static final String FISCAL_CODE_PATTERN =
            "[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d\\d[a-zA-Z]";
    public static final String PHONE_PATTERN = "^[0-9]+$"; //TODO VALIDATE REGEX "d{10}$"
    public static final String PLATE_PATTERN = "[a-zA-Z]{2}\\d{3,4}[a-zA-Z]{2}$";
    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9][a-zA-Z0-9_.-]{2,14}$";
    public static final String NAME_PATTERN = "^[\\p{L}\\s'.-]+$";
    public static final String ADDRESS_PATTERN = "^[0-9\\p{L}\\s'.,-]+$"; //TODO VALIDATE REGEX
    public static final String STRONG_PASSWORD_PATTERN =
            "(?-i)(?=^.{8,}$)((?!.*\\s)(?=.*[A-Z])(?=.*[a-z]))((?=(.*\\d){1,})|(?=(.*\\W){1,}))^.*$";
    public static final String INTEGER_PATTERN = "^[0-9]+$";
    public static final String AGENCY_CODE_PATTERN = "[a-zA-Z0-9]{5}$";
    public static final String ALPHANUMERIC = "[a-zA-Z0-9]+$";
    // General Email Regex (RFC 5322 Official Standard)
    public static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)" +
            "*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-" +
            "\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:" +
            "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]" +
            "*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c" +
            "\\x0e-\\x7f])+)\\])";


    private final String NULL_FIELD = "Il campo %s non pu\u00F2 essere vuoto.";
    private final String NOT_VALID = "Il campo %s non \u00E8 valido.";
    private final String NOT_EQUALS = "%s deve essere uguale a %s";
    private final String NOT_SELECTED = "Il campo %s deve essere selezionato.";

    public List<String> getErrors() {
        return errors;
    }

    public static boolean isValid(String str, String pattern) {
        return str.matches(pattern);
    }

    private boolean validateEquals(String primaryStr, String secondaryStr) {
        return Objects.equals(primaryStr, secondaryStr);
    }


    public void validateField(String pattern, TextField tf, ImageView iv, Label label) {
        String name = tf.getText().trim();

        if (Objects.equals(name, "")) {
            errors.add(getValidationError(NULL_FIELD, label));
            showErrorIcon(iv);
        } else if (isValid(name, pattern)) {
            showValidIcon(iv);
        } else {
            errors.add(getValidationError(NOT_VALID, label));
            showErrorIcon(iv);
        }
    }

    public void isEmpty(TextField tf, ImageView iv, Label label) {
        String name = tf.getText().trim();
        if (Objects.equals(name, "")) {
            errors.add(getValidationError(NULL_FIELD, label));
            showErrorIcon(iv);
        } else
            showValidIcon(iv);
    }

    public void isSelected(ToggleGroup tg, ImageView iv, Label label) {
        if (tg.getSelectedToggle() == null) {
            errors.add(getValidationError(NOT_SELECTED, label));
            showErrorIcon(iv);
        } else {
            showValidIcon(iv);
        }
    }

    public void validateProductionYear(TextField tf, ImageView iv, Label label) {
        String yearStr = tf.getText().trim();
        int year = 0;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        if (!Objects.equals(yearStr, "")) {
            try {
                year = Integer.parseInt(yearStr);
            } catch (NumberFormatException e) {
                // skip
            }
        }

        if (Objects.equals(yearStr, "")) {
            errors.add(getValidationError(NULL_FIELD, label));
            showErrorIcon(iv);
        } else if (year >= 1950 && year <= currentYear) {
            showValidIcon(iv);
        } else {
            errors.add(getValidationError(NOT_VALID, label));
            showErrorIcon(iv);
        }
    }

    public void validateOptionalField(String pattern, TextField tf, ImageView iv, Label label) {
        if (isValid(tf.getText(), pattern)) {
            showValidIcon(iv);
        } else {
            errors.add(getValidationError(NOT_VALID, label));
            showErrorIcon(iv);
        }
    }

    public void validatePasswordRepeat(TextField primaryField, TextField secondaryField,
                                        ImageView iv, Label primaryLabel, Label secondaryLabel) {
        String primaryStr = primaryField.getText().trim();
        String secondaryStr = secondaryField.getText().trim();

        if (!validateEquals(primaryStr, secondaryStr)) {
            errors.add(getValidationError(primaryLabel, secondaryLabel));
            showErrorIcon(iv);
        }
        else if (Objects.equals(secondaryStr, "")) {
            showErrorIcon(iv);
        }
        else
            showValidIcon(iv);
    }

    public void validateDate(ChronoLocalDate fromDate, ChronoLocalDate toDate, DatePicker dp, ImageView iv, Label label) {
        LocalDate date = dp.getValue();

        if (dp.getValue() == null) {
            errors.add(getValidationError(NULL_FIELD, label));
            showErrorIcon(iv);
            return;
        }

        boolean correct = true;
        if (fromDate == null && toDate == null) {
            showValidIcon(iv);
            return;
        }

        // all days before specified date are invalid
        else if (fromDate == null) {
            if (date.isBefore(toDate)) {
                correct = false;
            }
        }
        // all days after specified date are invalid
        else if (toDate == null) {
            if (date.isAfter(fromDate)) {
                correct = false;
            }
        }
        // all days outside specified date interval are invalid
        else {
            if (date.isAfter(fromDate) && date.isBefore(toDate)) {
                correct = false;
            }
        }
        // show validation icon and add error if any
        if(correct) {
            showValidIcon(iv);
        }
        else {
            errors.add(getValidationError(NOT_VALID, label));
            showErrorIcon(iv);
        }
    }



    private String getValidationError(String pattern, Label label) {
        return String.format(pattern, label.getText());
    }

    private String getValidationError(Label primary, Label secondary) {
        return String.format(NOT_EQUALS, secondary.getText(), primary.getText());
    }

    private void showErrorIcon(ImageView imageView) {
        imageView.setImage(new Image("resources/images/error.png"));
    }

    private void showValidIcon(ImageView imageView) {
        imageView.setImage(new Image("resources/images/ok.png"));
    }

    // in case of false sets first found error to errorField
    public boolean hasErrors() {
        return errors.size() == 0;
    }

    public void showFirstError(Label label) {
        if (errors.size() != 0) {
            label.setText(errors.get(0));
        }
    }

    public void setValid(ImageView iv) {
        showValidIcon(iv);
    }


    public void validateTimeInterval(DatePicker fromDate, TimePicker fromTime, DatePicker toDate, TimePicker toTime,
                                     ImageView iv, Label label) {

        if(fromDate.getValue() == null && toDate.getValue() == null) {
            return;
        }

        if (fromDate.getValue().equals(toDate.getValue())) {

            if (fromTime.getValue().isAfter(toTime.getValue()) || fromTime.getValue().equals(toTime.getValue())) {
                errors.add(getValidationError(NOT_VALID, label));
                showErrorIcon(iv);
            }

        }
        else {
            showValidIcon(iv);
        }
    }
    */
}
