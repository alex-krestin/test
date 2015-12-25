package it.uniba.carloan.dao.exception;


import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.helper.exception.Noticeable;

import static javafx.scene.control.Alert.AlertType.WARNING;

public class PersistenceException extends Exception implements Noticeable {

    public PersistenceException() {}

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }

    @Override
    public void showAlert() {
        AlertHandler.showAlert(WARNING, "Attention!", "There is some problem with database access. " +
                "Please, control your connection and retry.");
    }
}
