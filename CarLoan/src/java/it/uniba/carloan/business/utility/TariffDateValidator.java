package it.uniba.carloan.business.utility;


import it.uniba.carloan.entity.Tariff;
import it.uniba.carloan.presentation.helper.exception.InvalidTariffDateException;

import java.time.LocalDate;
import java.util.List;

public class TariffDateValidator<T extends Tariff> {

    public void validate(Tariff tariff, List<T> checkList) throws InvalidTariffDateException {
        LocalDate testFromDate = tariff.getFromDate();
        LocalDate testToDate = tariff.getToDate();

        boolean overlap;

        for (Tariff record: checkList) {
            overlap = DateChecker.isOverlapped(record.getFromDate(), record.getToDate(), testFromDate, testToDate);
            if(overlap) {
                throw new InvalidTariffDateException();
            }
        }
    }
}
