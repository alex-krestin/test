package utility;


public class Constants {
    public enum Sex { M, F }

    public enum AccountType { OPERATOR, ADMIN }

    public enum Status {AVAILABLE, NOT_AVAILABLE, IN_SERVICE, REMOVED }
    public static final String DB_CONFIG_FILE = "db.config"; // path to the config file
    public static final String MYSQL = "1";
    public static final String SQLITE = "2";
    public static final int MIN_AGE = 21;

}
