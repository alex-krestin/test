package presentation.controller;

import javafx.fxml.Initializable;
import presentation.FrontController;

import java.util.Objects;


public class ExistentDatabaseChoiceController extends DatabaseChoiceController implements Initializable {

    @Override
    public void nextView() {
        if(Objects.equals(fx_database_type.getValue(), "MySQL"))
            FrontController.handleRequest("ExistentMysqlConfig");
    }

}
