package presentation;

import javafx.application.Application;
import javafx.stage.Stage;
import utility.DBConfig;
import utility.DBConfigObject;
import utility.MySQL_DBConfig;
import utility.SessionData;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp extends Application {
    private static final Logger log = Logger.getLogger(MainApp.class.getName());

    @Override
    public void start(Stage primaryStage) {

        //TODO get dynamicly
        String databaseType = "MYSQL";

        if(!DBConfig.isExists()) {

            log.log(Level.INFO, "Config file doesn't exists. Creating a new one.");

            switch (databaseType) {
                case "MYSQL":
                    MySQL_DBConfig orig = new MySQL_DBConfig("localhost", "carloan", "java", "pass123");
                    try {
                        DBConfig.createConfig(orig);
                    } catch (IOException | NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException e) {
                        log.log(Level.SEVERE, "Error on creating config file: ", e);
                    }
                    break;
                default:
                    break;
            }

        }

        // read config
       // DBConfigObject config = DBConfig.readConfig();

        DBConfigObject config = new MySQL_DBConfig("localhost", "carloan", "java", "pass123");

        SessionData.setDbco(config);

        SessionData.setUserType("admin");
        FrontController.handleRequest("Dashboard", true);

    }

}
