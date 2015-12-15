package entity;


import java.time.LocalDate;

public interface Tariff  {

    int getTariffID();
    LocalDate getFromDate();
    LocalDate getToDate();

}
