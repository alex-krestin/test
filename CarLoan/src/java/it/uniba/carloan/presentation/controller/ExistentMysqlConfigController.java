package it.uniba.carloan.presentation.controller;

import it.uniba.carloan.business.security.DatabaseConfig;
import it.uniba.carloan.business.utility.SessionData;
import it.uniba.carloan.dao.config.MySQLDatabaseConfig;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.FrontController;
import javafx.scene.control.Alert;

import static it.uniba.carloan.business.constants.DatabaseType.MYSQL;
import static it.uniba.carloan.presentation.helper.EntityCode.CONNECTION;
import static it.uniba.carloan.presentation.helper.OperationCode.TEST;


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

        MySQLDatabaseConfig databaseConfig = new MySQLDatabaseConfig(host, port, database, username, password, tablePrefix);
        Response request = FrontController.handleRequest(TEST, CONNECTION, databaseConfig);

        if(request.getOperationResult()) {
            SessionData.config = new DatabaseConfig<>(MYSQL, databaseConfig);
            //TODO create config file
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Info", "Connected");
        }
        else
            AlertHandler.showAlert(Alert.AlertType.ERROR, "Errore", "Connessione al database non \u00E8 riuscita. Controlla i dati e riprova.");
    }
}
