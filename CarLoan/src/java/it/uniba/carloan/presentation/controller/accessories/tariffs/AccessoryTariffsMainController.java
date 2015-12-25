package it.uniba.carloan.presentation.controller.accessories.tariffs;


import it.uniba.carloan.entity.AccessoryTariff;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.ACCESSORY_TARIFF;
import static it.uniba.carloan.presentation.helper.OperationCode.GET_ALL;

public class AccessoryTariffsMainController extends TariffController implements Initializable {
    public TableView<AccessoryTariff> table;
    public TableColumn<AccessoryTariff, String> categoryColumn;
    public TableColumn<AccessoryTariff, String> dailyPriceColumn;
    public TableColumn<AccessoryTariff, String> maxDaysColumn;
    public TableColumn<AccessoryTariff, String> fromDateColumn;
    public TableColumn<AccessoryTariff, String> toDateColumn;

    static AccessoryTariff currentTariff = null;
    private ObservableList<AccessoryTariff> loadedData = FXCollections.observableArrayList();

    public void openAddAccessoryTariff() {
        if (loadedData.size() > 0) {
            FrontController.handleRequest("AddAccessoryTariff", "Nuovo tariffo");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Prima di procedere è neccessario " +
                    "aggiungere almeno una categoria!");
        }
    }

    public void openEditAccessoryTariff() {
        openEditTariff(currentTariff, "Accessory");
    }

    public void deleteAccessoryTariff() {
        deleteItem(currentTariff, ACCESSORY_TARIFF);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // reset currentAgency every time AgenciesView is loaded
        currentTariff = null;

        // set table columns
        categoryColumn.setCellValueFactory(tariff ->
                new SimpleStringProperty(tariff.getValue().getCategoryName()));

        dailyPriceColumn.setCellValueFactory(tariff ->
                new SimpleStringProperty(StringConverter.formatToCurrency(tariff.getValue().getDailyPrice())));

        maxDaysColumn.setCellValueFactory(new PropertyValueFactory<>("maxDays"));

        fromDateColumn.setCellValueFactory(tariff ->
                new SimpleStringProperty(StringConverter.formatLocalDate(tariff.getValue().getFromDate())));

        toDateColumn.setCellValueFactory(tariff ->
                new SimpleStringProperty(StringConverter.formatLocalDate(tariff.getValue().getToDate())));

        DataLoader<AccessoryTariff> dataLoader = new DataLoader<AccessoryTariff>() {
            @Override
            protected Response dataLoadRequest() {
                return FrontController.handleRequest(GET_ALL, ACCESSORY_TARIFF, null);
            }

            @Override
            protected HBox spinnerBox() {
                return spinnerBox;
            }
        };

        loadedData = dataLoader.getLoadedData();
        // insert users to the table
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
