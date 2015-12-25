package it.uniba.carloan.entity;


import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class StringConverter {

    private StringConverter() {}

    public static String formatLocalDate(LocalDate value) {
        if (value == null) {
            return "";
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return value.format(format);
    }

    public static String formatToCurrency(BigDecimal value) {
        if (value == null) {
            value = new BigDecimal(0);
        }
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
        return currencyFormatter.format(value);
    }



}
