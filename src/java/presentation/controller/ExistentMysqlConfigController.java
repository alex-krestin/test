package presentation.controller;

import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;
import utility.MySQL_DBConfig;
import utility.SessionData;

import static presentation.helper.EntityCode.CONNECTION;
import static presentation.helper.OperationCode.TEST;


public class ExistentMysqlConfigController extends MysqlConfigController {

    public void previousView() {
        FrontController.handleRequest("ExistentDatabaseChoice");
    }

    public void nextView() {
        String host = fx_hostname.getText();
        int port = Integer.parseInt(fx_port.getText());
        String database = fx_database.getText();
        String username = fx_username.getText();
        String password = fx_password.getText();
        String tablePrefix = fx_table_prefix.getText();

        MySQL_DBConfig config = new MySQL_DBConfig(host, port, database, username, password, tablePrefix);

        // save temporal data to session
        SessionData.setDbco(config);

        TransferObject request = FrontController.handleRequest(TEST, CONNECTION, config);

        if(request.getOperationResult()) {
            //TODO create config file
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Info", "Connected");
        }
        else
            AlertHandler.showAlert(Alert.AlertType.ERROR, "Errore", "Connessione al database non \u00E8 riuscita. Controlla i dati e riprova.");
    }
}
