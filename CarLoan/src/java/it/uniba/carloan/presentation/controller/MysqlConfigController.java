package it.uniba.carloan.presentation.controller;

import it.uniba.carloan.business.security.DatabaseConfig;
import it.uniba.carloan.business.utility.SessionData;
import it.uniba.carloan.dao.config.MySQLDatabaseConfig;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.FrontController;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.business.constants.DatabaseType.MYSQL;
import static it.uniba.carloan.presentation.helper.EntityCode.CONNECTION;
import static it.uniba.carloan.presentation.helper.OperationCode.TEST;


public class MysqlConfigController extends DefaultController implements Initializable {
    public Button prev_btn;
    public Button next_btn;
    public Button exit_btn;
    public TextField fx_hostname;
    public TextField fx_port;
    public TextField fx_username;
    public TextField fx_password;
    public TextField fx_database;
    public TextField fx_table_prefix;
    public CheckBox fx_delete_check;

    public void previousView() {
        FrontController.handleRequest("DatabaseChoice");
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
            FrontController.handleRequest("AddFirstAccount");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.ERROR, "Errore", "Connessione al database non \u00E8 riuscita. Controlla i dati e riprova.");
        }
    }

    public void exit() {
        Boolean result = AlertHandler.showAlertConfirmationDialog("Attenzione", "Sei sicuro di uscire", "Il file di configurazione " +
                "non \u00E8 ancora creato. Dovrai ripetere la procedura di configurazione.", "Esci");

        if(result)
            closeWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        MySQLDatabaseConfig config = (MySQLDatabaseConfig) SessionData.config.getDatabaseConfig();

        // load configuration from session data if exists
        if(config != null) {
            fx_hostname.setText(config.getHost());
            fx_port.setText(String.valueOf(config.getPort()));
            fx_username.setText(config.getUser());
            fx_password.setText(config.getPassword());
            fx_database.setText(config.getDatabase());
            fx_table_prefix.setText(config.getTablePrefix());
        }
        else {
            // Set default values
            fx_hostname.setText("localhost");
            fx_port.setText("3306");
            fx_table_prefix.setText("carloan_");
        }

        // Control if all necessary fields are filling
        ObservableValue<? extends Boolean> bb = new BooleanBinding() {
            {
                super.bind(fx_hostname.textProperty(),
                        fx_port.textProperty(),
                        fx_username.textProperty(),
                        fx_database.textProperty(),
                        fx_table_prefix.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (fx_hostname.getText().isEmpty()
                        || fx_port.getText().isEmpty()
                        || fx_username.getText().isEmpty()
                        || fx_database.getText().isEmpty())
                        || fx_table_prefix.getText().isEmpty();
            }
        };

        next_btn.disableProperty().bind(bb);
    }

}

