package it.uniba.carloan.presentation.controller.tariffs;


import it.uniba.carloan.entity.Tariff;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.FrontController;
import it.uniba.carloan.presentation.controller.DefaultController;
import javafx.scene.control.Alert;

public class TariffController extends DefaultController {

    public void gotoCarTariffs() {
        FrontController.handleRequest("CarTariffs");
    }

    public void gotoAccessoryTariffs() {
        FrontController.handleRequest("AccessoryTariffs");
    }

    public void gotoServiceTariffs() {
        FrontController.handleRequest("ServiceTariffs");
    }

    public void gotoPenaltyTariffs() {
        FrontController.handleRequest("PenaltyTariffs");
    }

    protected void openEditTariff(Tariff currentTariff, String tariffType) {
        if (currentTariff != null) {
            if (currentTariff.getId() == null) {
                FrontController.handleRequest("Add" + tariffType + "Tariff", "Nuovo tariffo");
            }
            else {
                FrontController.handleRequest("Edit" + tariffType + "Tariff", "Modifica tariffo");
            }
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessun tariffo selezionato!");
        }
    }
}
