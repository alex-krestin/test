package presentation.validation;


import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import presentation.validation.exception.GenericValidationException;
import presentation.validation.rules.RegexValidationRule;

import javax.activation.UnsupportedDataTypeException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GridValidator {
    private static final Logger log = Logger.getLogger(GridValidator.class.getName());

    private final GridPane grid;
    private Integer column;
    private Integer row;
    private final Integer lastColumnIndex;
    private final Integer lastRowIndex;
    private Map<ValidationRule, ValidationObject> rules;
    private String fieldName;
    private ValidationFlag flag = ValidationFlag.ALL;
    boolean showError = true;

    private List<String> localErrors;
    private final List<String> errors;

    public enum ValidationFlag {
        ALL, ONE
    }


    public GridValidator(GridPane gridPane) {
        grid = gridPane;
        lastColumnIndex = gridPane.getColumnConstraints().size() - 1;
        lastRowIndex = gridPane.getRowConstraints().size() - 1;
        rules = new LinkedHashMap<>();
        errors = new ArrayList<>();
        removeValidationIcons();
    }

    public GridValidator addConstraint(ValidationConstraint constraint) {
        ValidationRule rule = constraint.getValidationRule();
        ValidationObject object = new ValidationObject();
        rules.put(rule, object);
        return this;
    }

    public GridValidator addPattern(ValidationPattern pattern) {
        ValidationRule rule = new RegexValidationRule();
        ValidationObject object = new ValidationObject();
        object.setRegexPattern(pattern.getPattern());
        rules.put(rule, object);
        return this;
    }

    public GridValidator addOption(ValidationConstraint constraint, Object value) {
        ValidationRule rule = constraint.getValidationRule();
        ValidationObject vo = new ValidationObject();
        vo.setOptionValue(value);
        rules.put(rule, vo);
        return this;
    }

    public GridValidator addFlag(ValidationFlag validationFlag) {
        flag = validationFlag;
        return this;
    }

    public GridValidator showError(boolean value) {
        showError = value;
        return this;
    }


    public boolean validate(Node... nodes) {
        removeErrorText();
        localErrors = new ArrayList<>();

        for (Node node: nodes) {
            if (node != null) {
                column = getColumnIndex(node);
                row = getRowIndex(node);

                fieldName = getLabelText(column-1, row);
                rules.forEach((k,v) -> {
                    if (flag == ValidationFlag.ALL && localErrors.size() > 0) {
                        return;
                    }
                    try{
                        v.setMainObject(node);
                        v.setObjectName(fieldName);
                        k.validate(v);
                    } catch (GenericValidationException e) {
                        localErrors.add(e.getMessage());
                    } catch (UnsupportedDataTypeException e) {
                        log.log(Level.WARNING, "Validation is performed on an unsupported field: ", e);
                    }
                });
            }
        }

        boolean result = false;
        if (showError) {
            switch (flag) {
                case ALL:
                    if (localErrors.size() > 0) errors.addAll(localErrors);
                    else result = true;
                    break;

                case ONE:
                    if (localErrors.size() == nodes.length) errors.addAll(localErrors);
                    else result = true;
                    break;

                default:
                    log.log(Level.SEVERE, "Unsupported validation flag");
            }

            if(result) showValidIcon();
            else showErrorIcon();
        }

        else {
            if (localErrors.size() == 0) result = true;
            showError = true;
        }

        rules = new LinkedHashMap<>();
        flag = ValidationFlag.ALL;
        return result;
    }

    private String getLabelText(Integer column, Integer row) {
        Node node = getNodeByRowColumnIndex(column, row);

        if (node != null && node instanceof Label) {
            return ((Label) node).getText();
        }

        return null;
    }

    private Node getNodeByRowColumnIndex(Integer column, Integer row) {

        if(column == null || row == null) {
            return null;
        }
        try {
            for (Node node : grid.getChildren()) {
                if (node != null) {
                    Integer colInd = getColumnIndex(node);
                    Integer rowInd = getRowIndex(node);

                    if (column.equals(colInd) && row.equals(rowInd)) {
                        return node;
                    }
                }
            }
        } catch (NullPointerException e) {
            log.log(Level.WARNING, "Can't get row or column index from fxml: ", e);
        }

        return null;
    }

    private Integer getRowIndex(Node node) {
        if(GridPane.getRowIndex(node) == null) {
            return GridPane.getRowIndex(node.getParent());
        }
        else {
            return GridPane.getRowIndex(node);
        }
    }

    private Integer getColumnIndex(Node node) {
        if(GridPane.getColumnIndex(node) == null) {
            return GridPane.getColumnIndex(node.getParent());
        }
        else {
            return GridPane.getColumnIndex(node);
        }
    }

    private void showIcon(String filename) {
        removeIcon();
        ImageView imageView = new ImageView();
        imageView.setId("validation-" + row);
        imageView.setImage(new Image("resources/images/" + filename));
        grid.add(imageView, lastColumnIndex, row);
    }

    private void showErrorIcon() {
        showIcon("error.png");
    }

    private void showValidIcon() {
        showIcon("ok.png");
    }

    private void removeValidationIcons() {
        for (int row = 0; row < lastRowIndex; row++) {
            Node node = getNodeByRowColumnIndex(lastColumnIndex, row);
            if (node instanceof ImageView) {
                if (node.getId().equals("validation-" + row)) {
                    grid.getChildren().remove(node);
                }
            }
        }
    }

    private void removeIcon() {
        grid.getChildren().remove(getNodeByRowColumnIndex(lastColumnIndex, row));
    }

    private void removeErrorText() {
        grid.getChildren().remove(getNodeByRowColumnIndex(column, lastRowIndex));
    }

    private void showFirstError() {
        Label label = new Label(errors.get(0));
        label.setTextFill(Color.RED);
        grid.add(label, column, lastRowIndex);
    }

    public boolean isValid() {
        if (errors.size() > 0) {
            showFirstError();
            return false;
        }
        return true;
    }
}
