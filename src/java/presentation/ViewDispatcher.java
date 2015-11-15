package presentation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import presentation.controller.DefaultController;
import utility.SessionData;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ViewDispatcher {

    // Shows view in the same window
    public void showView(String view) {
        SessionData.setCurrentView(view);
        String path = "presentation/view/" + SessionData.getUserType() + "/" + view + ".fxml";

        URL rootUrl = getClass().getProtectionDomain().getCodeSource().getLocation();
        URL pathToView = null;

        try {
            pathToView = new URL(rootUrl, path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            assert pathToView != null;
            Parent root = FXMLLoader.load(pathToView);
            SessionData.getScene().setRoot(root);
        } catch (IOException e) {
            AlertHandler.showException("Errore di visualizzazione", e);
            e.printStackTrace();
        }
    }

    // Closes previous window and open a new one
    public void showView(String view, boolean resizable) {
        // Close previous stage if exists
        if (SessionData.getCurrentStage() != null) {
            SessionData.getCurrentStage().close();
        }

        // Create new stage
        Stage stage = new Stage();
        // Save current stage and view in session data
        SessionData.setCurrentStage(stage);
        SessionData.setCurrentView(view);

        String path = "presentation/view/" + SessionData.getUserType() + "/" + view + ".fxml";

        // Load layout from fxml file.
        FXMLLoader loader = new FXMLLoader();

        URL rootUrl = getClass().getProtectionDomain().getCodeSource().getLocation();
        URL pathToView = null;
        try {
            pathToView = new URL(rootUrl, path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        loader.setLocation(pathToView);
        VBox layout = null;
        try {
            layout = loader.load();
        } catch (Exception e) {
            AlertHandler.showException("Errore di visualizzazione", e);
            e.printStackTrace();
            //TODO add logging
        }

        // Show the scene containing the layout.
        assert layout != null;
        Scene scene = new Scene(layout);
        // Save current scene in session data
        SessionData.setScene(scene);
        stage.setScene(scene);
        stage.setTitle("CarLoan");
        stage.getIcons().add(new Image("resources/app_icon.png"));
        stage.setResizable(resizable);
        stage.show();
    }



    public void openPopupWindow(String view, String title) {
        String path = "presentation/view/" + SessionData.getUserType() + "/" + view + ".fxml";
        URL rootUrl = getClass().getProtectionDomain().getCodeSource().getLocation();
        URL pathToView = null;
        try {
            pathToView = new URL(rootUrl, path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        SessionData.setCurrentView(view);
        System.out.println(pathToView);

        try {
            Stage stage = new Stage();
            assert pathToView != null;
            Parent root = FXMLLoader.load(pathToView);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(title);
            stage.getIcons().add(new Image("resources/app_icon.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(SessionData.getScene().getWindow());
            SessionData.setCurrentScene(scene); // uses for close current window
            //stage.initOwner(btn.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException | RuntimeException e) {
            AlertHandler.showException("Errore di visualizzazione", e);
            e.printStackTrace();
        }
    }

}
