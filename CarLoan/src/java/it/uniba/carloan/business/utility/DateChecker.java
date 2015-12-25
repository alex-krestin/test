package it.uniba.carloan.business.utility;


import java.time.chrono.ChronoLocalDate;

public class DateChecker {
    public static boolean isOverlapped(ChronoLocalDate fromDate, ChronoLocalDate toDate, ChronoLocalDate testFromDate, ChronoLocalDate testToDate) {

        if (toDate == null)
            return testFromDate.isAfter(fromDate) ||
                    (testFromDate.isBefore(fromDate) && testToDate == null) ||
                    (testToDate != null && testToDate.isAfter(fromDate));

        else
            return (testFromDate.isAfter(fromDate) && testFromDate.isBefore(toDate)) ||
                    (testToDate != null && testToDate.isAfter(fromDate) && testToDate.isBefore(toDate));

    }
}
