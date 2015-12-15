package utility;

import entity.User;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SessionData {
    private static DBConfigObject dbco = null;
    //TODO delete next line
    public static String DAOFactory = "1";

    // public static String DAOFactory;
    private static Scene scene;
    public static String userType = "guest";
    private static Stage currentStage;
    public static String currentView;
    private static User currentUser;
    private static Scene currentScene; // uses for close current window

    public static int agencyID = 1; // agencyID of logged in user //TODO delete 1

    public static void setAgencyID(int agencyID) {
        SessionData.agencyID = agencyID;
    }

    public static void setCurrentRootView(String currentRootView) {
        SessionData.currentRootView = currentRootView;
    }

    public static String currentRootView;

    public static void setController(Object controller) {
        SessionData.controller = controller;
    }

    public static Object controller;

    public static String getCurrentView() {
        return currentView;
    }

    public static void setCurrentView(String currentView) {
        SessionData.currentView = currentView;
    }

    public static DBConfigObject getDbco() {
        return dbco;
    }

    public static void setDbco(DBConfigObject dbco) {
        SessionData.dbco = dbco;
    }

    public static void setDAOFactory(String daoFactory) {
        SessionData.DAOFactory = daoFactory;
    }

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene currentScene) {
        scene = currentScene;
    }

    public static String getUserType() {
        return userType;
    }

    public static void setUserType(String accountType) {
        userType = accountType;
    }

    public static Stage getCurrentStage() {
        return currentStage;
    }

    public static void setCurrentStage(Stage currentStage) {
        SessionData.currentStage = currentStage;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        SessionData.currentUser = currentUser;
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static void setCurrentScene(Scene currentScene) {
        SessionData.currentScene = currentScene;
    }
}
