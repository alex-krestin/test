package presentation.controller;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;


public class InputValidationController {

    private ArrayList<String> errors = new ArrayList<>();

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

    public ArrayList<String> getErrors() {
        return errors;
    }

    public int getNumErrors() {
        return errors.size();
    }

    public String getFirstError() {
        return errors.get(0);
    }

    private boolean validate(String str, String pattern) {
        return str.matches(pattern);
    }

    private boolean validateEquals(String primaryStr, String secondaryStr) {
        return Objects.equals(primaryStr, secondaryStr);
    }

    /**
     * @param tf    TextField to be validated
     * @param iv    related ImageView to show validation result
     * @param label Label of field to validate
     */
    public void validateField(String pattern, TextField tf, ImageView iv, Label label) {
        String name = tf.getText().trim();

        if (Objects.equals(name, "")) {
            errors.add(getValidationError(NULL_FIELD, label));
            showErrorIcon(iv);
        } else if (!validate(name, pattern)) {
            errors.add(getValidationError(NOT_VALID, label));
            showErrorIcon(iv);
        } else
            showValidIcon(iv);
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

    public void validateOptionalField(String pattern, TextField tf, ImageView iv, Label label) {
        if (!validate(tf.getText(), pattern)) {
            errors.add(getValidationError(NOT_VALID, label));
            showErrorIcon(iv);
        } else
            showValidIcon(iv);
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

    public void validateDate(LocalDate fromDate, LocalDate toDate, DatePicker dp, ImageView iv, Label label) {
        LocalDate date = dp.getValue();
        boolean correct = true;

        if (dp.getValue() == null) {
            errors.add(getValidationError(NULL_FIELD, label));
            showErrorIcon(iv);
            return;
        }

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

    public static void disableDateSelection(LocalDate fromDate, LocalDate toDate, DatePicker datePicker) {
        final String disabled = "-fx-opacity: 40%";

        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                // disable all days before specified date
                                if (fromDate == null) {
                                    if (item.isBefore(toDate)) {
                                        setDisable(true);
                                        setStyle(disabled);
                                    }
                                }
                                // disable all days after specified date
                                else if(toDate == null) {
                                    if (item.isAfter(fromDate)) {
                                        setDisable(true);
                                        setStyle(disabled);
                                    }
                                }
                                // disable days in specified interval
                                else {
                                    if (item.isAfter(fromDate) && item.isBefore(toDate)) {
                                        setDisable(true);
                                        setStyle(disabled);
                                    }
                                }
                            }
                        };
                    }
                };
        datePicker.setDayCellFactory(dayCellFactory);
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
}
