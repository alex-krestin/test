package javafx.scene.control;


public class CustomTextField extends TextField {

    public void setMaxLength(int length) {
        TextFormatter<Object> textFormatter = new TextFormatter<>(t -> {
                if (t.getControlNewText().length() > length) {
                t.setText(""); // negate the change
            }
            return t;
        });

        this.setTextFormatter(textFormatter);
    }

    public void setUppercase() {
        textProperty().addListener((observable, oldValue, newValue) -> {
            this.setText(newValue.toUpperCase());
        });
    }

    public void setText(int value) {
        this.setText(String.valueOf(value));
    }

    public void limitToNumbers() {
        this.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals("") && !newValue.matches("\\d*")) {
                this.setText(oldValue);
                this.positionCaret(this.getLength());
            }
        });
    }

    public Integer getIntegerValue() {
        Integer number;

        try {
            number = Integer.parseInt(getText());
        } catch (NumberFormatException e) {
            return null;
        }

        return number;
    }


}
