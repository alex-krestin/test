package it.uniba.carloan.presentation;

import it.uniba.carloan.business.utility.SessionData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

class ViewDispatcher {
    private static final Logger log = Logger.getLogger(ViewDispatcher.class.getName());

    // Shows view in the same window
    public void showView(String view) {
        SessionData.currentRootView = view;
        SessionData.currentView = view;
        String path = "it/uniba/carloan/presentation/view/" + SessionData.userType + "/" + view + ".fxml";

        URL rootUrl = getClass().getProtectionDomain().getCodeSource().getLocation();
        URL pathToView = null;

        try {
            pathToView = new URL(rootUrl, path);
        } catch (MalformedURLException e) {
            log.log(Level.SEVERE, "Invalid path to fxml: ", e);
        }

        try {
            assert pathToView != null;
            Parent root = FXMLLoader.load(pathToView);
            SessionData.scene.setRoot(root);
        } catch (IOException e) {
            AlertHandler.showException("Errore di visualizzazione", e);
            log.log(Level.SEVERE, "Failed load fxml: ", e);
        }
    }

    // Closes previous window and open a new one
    public void showView(String view, boolean resizable) {
        // Close previous stage if exists
        if (SessionData.currentScene != null) {
            SessionData.currentStage.close();
        }

        // Create new stage
        Stage stage = new Stage();
        // Save current stage and view in session data
        SessionData.currentStage = stage;
        SessionData.currentRootView = view;
        SessionData.currentView = view;

        String path = "it/uniba/carloan/presentation/view/" + SessionData.userType + "/" + view + ".fxml";

        // Load layout from fxml file.
        FXMLLoader loader = new FXMLLoader();

        URL rootUrl = getClass().getProtectionDomain().getCodeSource().getLocation();
        URL pathToView = null;
        try {
            pathToView = new URL(rootUrl, path);
        } catch (MalformedURLException e) {
            log.log(Level.SEVERE, "Invalid path to fxml: ", e);
        }

        loader.setLocation(pathToView);
        VBox layout = null;
        try {
            layout = loader.load();
        } catch (Exception e) {
            AlertHandler.showException("Errore di visualizzazione", e);
            log.log(Level.SEVERE, "Failed load fxml: ", e);
        }

        // Show the scene containing the layout.
        assert layout != null;
        Scene scene = new Scene(layout);
        // Save current scene in session data
        SessionData.scene = scene;
        stage.setScene(scene);
        stage.setTitle("CarLoan");
        stage.getIcons().add(new Image("it/uniba/carloan/resources/app_icon.png"));
        stage.setResizable(resizable);
        stage.show();
    }



    public void openPopupWindow(String view, String title) {
        String path = "it/uniba/carloan/presentation/view/" + SessionData.userType + "/" + view + ".fxml";
        URL rootUrl = getClass().getProtectionDomain().getCodeSource().getLocation();
        URL pathToView = null;
        try {
            pathToView = new URL(rootUrl, path);
        } catch (MalformedURLException e) {
            log.log(Level.SEVERE, "Invalid path to fxml: ", e);
        }

        SessionData.currentView = view;

        try {
            Stage stage = new Stage();
            assert pathToView != null;
            Parent root = FXMLLoader.load(pathToView);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(title);
            stage.getIcons().add(new Image("it/uniba/carloan/resources/app_icon.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(SessionData.scene.getWindow());
            SessionData.currentScene = scene; // uses for close current window
            stage.showAndWait();
        } catch (IOException | RuntimeException e) {
            AlertHandler.showException("Errore di visualizzazione", e);
            log.log(Level.SEVERE, "Failed load fxml: ", e);
        }
    }

}
