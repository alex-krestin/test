import java.util.logging.Logger;


public class Main {
    private static Logger log = Logger.getLogger(Main.class.getName());

    private static String databaseType = "MYSQL"; // must be read from ui
    private static String MYSQL = "1";

    /*
    public static void main() {




        //log.info("Start");

        SessionData.setDAOFactory("1");

        if(!DBConfig.isExists()) {
            log.info("No config file found.");

            switch (databaseType) {
                case "MYSQL":
                    MySQL_DBConfig orig = new MySQL_DBConfig("localhost", "carloan", "java", "pass123");
                    DBConfig.createConfig(orig);
                    log.info("Created new config file.");
                    break;
                default:
                    log.severe("FUCK");
            }

        }

        else {
            DBConfigObject config = DBConfig.readConfig();
            SessionData.setDbco(config);
        //    System.out.println(config);
    }

        UserDAO dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getUserDAO();

        User user = dao.getUser(20);

        System.out.println(user.getUsername());

    }

}*/



}

