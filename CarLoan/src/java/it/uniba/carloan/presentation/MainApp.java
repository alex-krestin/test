package it.uniba.carloan.presentation;

import it.uniba.carloan.business.constants.DatabaseType;
import it.uniba.carloan.business.security.*;
import it.uniba.carloan.business.utility.SessionData;
import it.uniba.carloan.dao.DAOFactory;
import it.uniba.carloan.dao.config.MySQLDatabaseConfig;
import it.uniba.carloan.dao.exception.PersistenceException;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.constants.DatabaseType.MYSQL;

public class MainApp extends Application {
    private static final Logger log = Logger.getLogger(MainApp.class.getName());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //TODO get dynamicly
        DatabaseType databaseType = MYSQL;

        if(!SystemConfigurator.isExists()) {

            log.log(Level.INFO, "Config file doesn't exists. Creating a new one.");

            switch (databaseType) {
                case MYSQL:
                    MySQLDatabaseConfig orig = new MySQLDatabaseConfig("localhost", "carloan", "java", "pass123");
                    DatabaseConfig<MySQLDatabaseConfig> databaseConfig = new DatabaseConfig<>(MYSQL, orig);

                    try {
                        ConfigEncrypter<DatabaseConfig> encrypter = new ConfigEncrypter<>();
                        encrypter.createConfig(databaseConfig);

                    } catch (IOException | NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException e) {
                        log.log(Level.SEVERE, "Error on creating config file: ", e);
                    }
                    break;
                default:
                    break;
            }
        }

        // read config
        SessionData.config = ConfigDecrypter.readConfig();
        DatabaseConfigObject configObject = SessionData.config.getDatabaseConfig();


        try {
            DAOFactory.getDAOFactory().getDatabaseConnector().setDataSource(configObject);
        } catch (PersistenceException e) {
            log.log(Level.SEVERE, "", e);
        }


        SessionData.userType = "admin";
        FrontController.handleRequest("Dashboard", true);

    }

}
