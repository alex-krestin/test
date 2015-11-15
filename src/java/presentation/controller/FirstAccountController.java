package presentation.controller;

import entity.User;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.controller.users.AddUserController;
import presentation.controller.users.UserMainController;
import utility.SessionData;

import java.net.URL;
import java.util.ResourceBundle;


public class FirstAccountController extends AddUserController implements Initializable {

    public Button next_btn;
    public Button exit_btn;

    public void previousView() {
        FrontController.handleRequest("MysqlConfig");
    }

    public void nextView() {
        if(addUserValidateInput()) {
            String name =  fx_name.getText();
            String surname = fx_surname.getText();
            String username = fx_username.getText();
            String password = fx_password.getText();

            User user = new User(name, surname, username, password, "ADMIN", true, null);
            SessionData.setCurrentUser(user);

            //TODO add view with controller witch will create database
        }

    }

    public void exit() {
        Boolean result = AlertHandler.showAlertConfirmationDialog("Attenzione", "Sei sicuro di uscire", "Il file di " +
                "configurazione non \u00E8 ancora creato. Dovrai ripetere la procedura di configurazione.",
                "Esci");

        if(result)
            closeWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = SessionData.getCurrentUser();

        // load previous user data from session if exists
        if(user != null) {
            fx_name.setText(user.getName());
            fx_surname.setText(user.getSurname());
            fx_username.setText(user.getUsername());
        }

        // Control if all necessary fields are filling
        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(fx_name.textProperty(),
                        fx_surname.textProperty(),
                        fx_username.textProperty(),
                        fx_password.textProperty(),
                        fx_repeat_password.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (fx_name.getText().isEmpty()
                        || fx_surname.getText().isEmpty()
                        || fx_username.getText().isEmpty()
                        || fx_password.getText().isEmpty())
                        || fx_repeat_password.getText().isEmpty();
            }
        };

        next_btn.disableProperty().bind(bb);
    }


}
