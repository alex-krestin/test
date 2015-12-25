package it.uniba.carloan.entity;


import java.time.LocalDate;

public interface Tariff  {
    Integer getId();
    LocalDate getFromDate();
    LocalDate getToDate();

}
