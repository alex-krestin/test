package presentation.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import presentation.FrontController;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class DatabaseChoiceController extends DefaultController implements Initializable {

    public Button prev_btn;
    public Button next_btn;
    public Button exit_btn;
    public ComboBox<String> fx_database_type;

    public void checkDB() {
        if(fx_database_type.getValue() != null) {
            next_btn.disableProperty().setValue(false);
        }
    }

    public void previousView() {
        FrontController.handleRequest("GetStarted");
    }

    public void nextView() {
        if(Objects.equals(fx_database_type.getValue(), "MySQL"))
            FrontController.handleRequest("MysqlConfig");
    }

    public void exit() {
        closeWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_database_type.getItems().addAll(
                "MySQL",
                "SQLite"
        );
    }
}
