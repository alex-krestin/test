package presentation;

import dao.entity.SystemDAO;
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

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {

        //TODO get dynamicly
        String databaseType = "MYSQL";

        if(!DBConfig.isExists()) {

            System.out.println("Config doesnt exists");

            switch (databaseType) {
                case "MYSQL":
                    MySQL_DBConfig orig = new MySQL_DBConfig("localhost", "carloan", "java", "pass123");
                    try {
                        DBConfig.createConfig(orig);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    }
                   // System.out.println("");
                    break;
                default:
                    break;
            }

        }

        // read config
        DBConfigObject config = null;

        config = DBConfig.readConfig();

        SessionData.setDbco(config);

        SessionData.setUserType("admin");
        FrontController.handleRequest("Dashboard", true);

    }

    public static void main(String[] args) {
        launch(args);
    }

}
