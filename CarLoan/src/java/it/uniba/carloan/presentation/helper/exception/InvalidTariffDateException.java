package it.uniba.carloan.presentation.helper.exception;

import it.uniba.carloan.presentation.AlertHandler;

import static javafx.scene.control.Alert.AlertType.WARNING;

public final class InvalidTariffDateException extends Exception implements Noticeable {
    @Override
    public void showAlert() {
        AlertHandler.showAlert(WARNING, "Attenzione!", "Le date scelte si sovrappongono " +
                "con la tariffa esistente");
    }
}
