package it.uniba.carloan.presentation.controller;

import it.uniba.carloan.business.utility.SessionData;
import it.uniba.carloan.entity.User;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.FrontController;
import it.uniba.carloan.presentation.controller.users.AddUserController;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;


public class FirstAccountController extends AddUserController implements Initializable {

    public Button next_btn;
    public Button exit_btn;

    public void previousView() {
        FrontController.handleRequest("MysqlConfig");
    }

    @SuppressWarnings("LawOfDemeter")
    public void nextView() {
        if(addUserValidateInput()) {

            SessionData.currentUser = new User.Builder()
                    .name(fx_name.getText())
                    .surname(fx_surname.getText())
                    .username(fx_username.getText())
                    .password(fx_password.getText())
                    .accountType("ADMIN").build();

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

    @SuppressWarnings("LawOfDemeter")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = SessionData.currentUser;

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
