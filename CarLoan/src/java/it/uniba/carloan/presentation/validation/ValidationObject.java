package it.uniba.carloan.presentation.validation;


public class ValidationObject {
    private Object mainObject;
    private Object optionValue;
    private Object secondValue;
    private String objectName;

    public String getRegexPattern() {
        return regexPattern;
    }

    public void setRegexPattern(String regexPattern) {
        this.regexPattern = regexPattern;
    }

    private String regexPattern;

    public Object getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(Object optionValue) {
        this.optionValue = optionValue;
    }

    public String getObjectName() {
        return objectName;
    }

    void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public Object getMainObject() {
        return mainObject;
    }

    void setMainObject(Object mainObject) {
        this.mainObject = mainObject;
    }

    public Object getSecondValue() {
        return secondValue;
    }

    void setSecondValue(Object secondValue) {
        this.secondValue = secondValue;
    }
}
