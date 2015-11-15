package presentation.controller;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import presentation.FrontController;
import utility.SessionData;


public class DefaultController {

    public HBox spinnerBox;

    public void gotoDashboard() {
        FrontController.handleRequest("Dashboard");
    }

    public void gotoCars() {
        FrontController.handleRequest("Cars");
    }

    public void gotoUsers() {
        FrontController.handleRequest("Users");
    }

    public void gotoAgencies() {
        FrontController.handleRequest("Agencies");
    }

    public void gotoServices() {
        FrontController.handleRequest("Services");
    }

    public void gotoAccessories() {
        FrontController.handleRequest("Accessories");
    }

    public void gotoPenalties() {
        FrontController.handleRequest("Penalties");
    }

    public void gotoClients() {
        FrontController.handleRequest("Clients");
    }

    public void startFirst(String method) {} // TODO refactor and delete

    public void closeWindow() {
        Stage stage = (Stage) SessionData.getCurrentScene().getWindow();
        stage.close();
        // reset current scene
        SessionData.setCurrentScene(SessionData.getScene());
    }

}
