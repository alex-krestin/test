package it.uniba.carloan.presentation.controller;

import it.uniba.carloan.presentation.FrontController;
import javafx.fxml.Initializable;

import java.util.Objects;


public class ExistentDatabaseChoiceController extends DatabaseChoiceController implements Initializable {

    @Override
    public void nextView() {
        if(Objects.equals(fx_database_type.getValue(), "MySQL"))
            FrontController.handleRequest("ExistentMysqlConfig");
    }

}
