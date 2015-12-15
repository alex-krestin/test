package javafx.scene.control;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class TimePicker extends ComboBox<LocalTime> {

    private final ObservableList<LocalTime> defaultValues = FXCollections.observableArrayList();

    public TimePicker() {
        this(null);
        createDefaultValuesList();
        setItems(defaultValues);

        setConverter(new StringConverter<LocalTime>() {

            @Override
            public String toString(LocalTime time) {
                if (time == null){
                    return "";
                } else {
                    return time.toString();
                }
            }

            @Override
            public LocalTime fromString(String s) {
                DateTimeFormatter parseFormat = new DateTimeFormatterBuilder().appendPattern("H:mm").toFormatter();

                LocalTime localTime;

                try {
                    localTime = LocalTime.parse(s, parseFormat);
                } catch (DateTimeException ex) {
                    return null;
                }

                return localTime;
            }
        });


        valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidTime(newValue)) {
                setValue(oldValue);
            }
        });

    }

    public TimePicker(LocalTime localTime) {
        setValue(localTime);
    }


    private boolean isValidTime(LocalTime time) {
        try {
            time.getHour();
            time.getMinute();
            return true;
        } catch (NullPointerException | DateTimeException ex) {
            return false;
        }
    }

    private void createDefaultValuesList() {
        LocalTime time = LocalTime.of(23,30);
        int interval = 30;

        for(int i=0; i<48; i++){
            time = time.plusMinutes(interval);
            defaultValues.add(time);
        }

        this.getEditor();
    }



}
