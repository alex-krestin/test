package it.uniba.carloan.presentation.controller;

import it.uniba.carloan.presentation.FrontController;
import javafx.scene.control.Button;


public class GetStartedController extends DefaultController {

    public Button exit_btn;
    public Button new_db_btn;
    public Button connect_btn;

    public void handleCreateAction() {
        FrontController.handleRequest("DatabaseChoice");
    }

    public void handleConnectAction() {
        FrontController.handleRequest("ExistentDatabaseChoice");
    }


}
