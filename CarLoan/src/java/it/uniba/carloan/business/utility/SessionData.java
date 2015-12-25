package it.uniba.carloan.business.utility;

import it.uniba.carloan.business.security.DatabaseConfig;
import it.uniba.carloan.entity.User;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SessionData {
    public static DatabaseConfig config = null;
    public static Scene scene = null;
    public static String userType = "guest";
    public static Stage currentStage = null;
    public static Scene currentScene = null; // uses for close current window
    public static String currentRootView = null;
    public static String currentView = null;
    public static User currentUser = null;
    public static int agencyID = 1; // agencyID of logged in user //TODO delete 1
}
