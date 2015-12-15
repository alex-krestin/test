package dao.mysql;


import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;

public class MySQLDateUtility {

    public static LocalDate convertToLocalDate(Date sqlDate) {
        try {
            return sqlDate.toLocalDate();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static Date convertToSqlDate(ChronoLocalDateTime localDateTime) {
        return new java.sql.Date(localDateTime.atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli());
    }

}
