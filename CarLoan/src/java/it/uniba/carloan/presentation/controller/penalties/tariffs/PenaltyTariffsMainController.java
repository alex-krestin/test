package it.uniba.carloan.presentation.controller.penalties.tariffs;


import it.uniba.carloan.entity.PenaltyTariff;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.StringConverter;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.FrontController;
import it.uniba.carloan.presentation.controller.DataLoader;
import it.uniba.carloan.presentation.controller.tariffs.TariffController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.PENALTY_TARIFF;
import static it.uniba.carloan.presentation.helper.OperationCode.GET_ALL;

public class PenaltyTariffsMainController extends TariffController implements Initializable {
    public TableView<PenaltyTariff> table;
    public TableColumn<PenaltyTariff, String> penaltyColumn;
    public TableColumn<PenaltyTariff, String> categoryColumn;
    public TableColumn<PenaltyTariff, String> priceColumn;
    public TableColumn<PenaltyTariff, String> fromDateColumn;
    public TableColumn<PenaltyTariff, String> toDateColumn;

    static PenaltyTariff currentTariff;
    private ObservableList<PenaltyTariff> loadedData = FXCollections.observableArrayList();

    public void openAddPenaltyTariff() {
        if (loadedData.size() > 0) {
            FrontController.handleRequest("AddPenaltyTariff", "Nuovo tariffo");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Prima di procedere è neccessario " +
                    "aggiungere almeno una penale!");
        }
    }

    public void openEditPenaltyTariff() {
        openEditTariff(currentTariff, "Penalty");
    }

    public void deletePenaltyTariff() {
        deleteItem(currentTariff, PENALTY_TARIFF);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // reset currentAgency every time AgenciesView is loaded
        currentTariff = null;

        // load all agencies from the database
        //getAccessoryTariffsList();

        // set table columns
        penaltyColumn.setCellValueFactory(tariff ->
                new SimpleStringProperty(tariff.getValue().getPenaltyTitle()));

        categoryColumn.setCellValueFactory(tariff ->
                new SimpleStringProperty(tariff.getValue().getPenaltyCategoryName()));

        priceColumn.setCellValueFactory(tariff ->
                new SimpleStringProperty(StringConverter.formatToCurrency(tariff.getValue().getPrice())));

        fromDateColumn.setCellValueFactory(tariff ->
                new SimpleStringProperty(StringConverter.formatLocalDate(tariff.getValue().getFromDate())));

        toDateColumn.setCellValueFactory(tariff ->
                new SimpleStringProperty(StringConverter.formatLocalDate(tariff.getValue().getToDate())));

        // insert users to the table
        DataLoader<PenaltyTariff> dataLoader = new DataLoader<PenaltyTariff>() {
            @Override
            protected Response dataLoadRequest() {
                return FrontController.handleRequest(GET_ALL, PENALTY_TARIFF, null);
            }

            @Override
            protected HBox spinnerBox() {
                return spinnerBox;
            }
        };

        loadedData = dataLoader.getLoadedData();
        table.setItems(loadedData);

        // add listener to the table rows
        table.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());
    }


    private class RowSelectChangeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            int rowIndex = (int) newValue;
            // update current user
            currentTariff = loadedData.get(rowIndex);
        }
    }


}
