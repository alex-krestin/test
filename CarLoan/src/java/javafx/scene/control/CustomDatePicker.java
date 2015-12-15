package javafx.scene.control;


import javafx.util.Callback;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class CustomDatePicker extends DatePicker {
    private final String disabled = "-fx-opacity: 40%";

    public void disableAfter(ChronoLocalDate date) {
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isAfter(date)) {
                                    setDisable(true);
                                    setStyle(disabled);
                                }
                            }
                        };
                    }
                };
        setDayCellFactory(dayCellFactory);
    }

    public void disableBefore(ChronoLocalDate date) {
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isBefore(date)) {
                                    setDisable(true);
                                    setStyle(disabled);
                                }
                            }
                        };
                    }
                };
        setDayCellFactory(dayCellFactory);
    }

    public void disableBetween(ChronoLocalDate startDate, ChronoLocalDate endDate) {

        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isAfter(startDate) && item.isBefore(endDate)) {
                                    setDisable(true);
                                    setStyle(disabled);
                                }
                            }
                        };
                    }
                };
        setDayCellFactory(dayCellFactory);
    }
}
