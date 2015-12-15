package presentation.controller;

import javafx.scene.control.Button;
import presentation.FrontController;


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
