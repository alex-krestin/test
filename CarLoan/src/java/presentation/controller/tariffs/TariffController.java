package presentation.controller.tariffs;


import entity.Tariff;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.controller.DefaultController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
            if (currentTariff.getTariffID() == 0) {
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


    protected void setFromDateColumn(TableColumn<? extends Tariff, String> column) {
        column.setCellValueFactory(tariff -> {
            String str = "";
            if (tariff.getValue().getFromDate() != null) {
                LocalDate date = tariff.getValue().getFromDate();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                str = date.format(format);
            }
            return new SimpleStringProperty(str);
        });
    }

    protected void setToDateColumn(TableColumn<? extends Tariff, String> column) {
        column.setCellValueFactory(tariff -> {
            String str = "";
            if (tariff.getValue().getToDate() != null) {
                LocalDate date = tariff.getValue().getToDate();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                str = date.format(format);
            }
            return new SimpleStringProperty(str);
        });
    }

}
