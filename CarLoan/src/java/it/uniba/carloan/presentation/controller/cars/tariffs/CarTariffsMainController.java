package it.uniba.carloan.presentation.controller.cars.tariffs;


import it.uniba.carloan.entity.CarTariff;
import it.uniba.carloan.entity.Response;
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

import static it.uniba.carloan.entity.StringConverter.formatLocalDate;
import static it.uniba.carloan.entity.StringConverter.formatToCurrency;
import static it.uniba.carloan.presentation.helper.EntityCode.CAR_TARIFF;
import static it.uniba.carloan.presentation.helper.OperationCode.GET_ALL;

public class CarTariffsMainController extends TariffController implements Initializable {
    public TableView<CarTariff> table;
    public TableColumn<CarTariff, String> categoryColumn;
    public TableColumn<CarTariff, String> dailyPriceColumn;
    public TableColumn<CarTariff, String> weeklyPriceColumn;
    public TableColumn<CarTariff, String> kmPriceColumn;
    public TableColumn<CarTariff, String> freeKmPriceColumn;
    public TableColumn<CarTariff, String> fromDateColumn;
    public TableColumn<CarTariff, String> toDateColumn;

    static CarTariff currentTariff;
    private ObservableList<CarTariff> loadedData = FXCollections.observableArrayList();


    public void openAddCarTariff() {
        if (loadedData.size() > 0) {
            FrontController.handleRequest("AddCarTariff", "Nuovo tariffo");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Prima di procedere è neccessario " +
                    "aggiungere almeno una categoria!");
        }
    }

    public void openEditCarTariff() {
        openEditTariff(currentTariff, "Car");
    }

    public void deleteCarTariff() {
        deleteItem(currentTariff, CAR_TARIFF);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // reset currentAgency every time AgenciesView is loaded
        currentTariff = null;

        // set table columns
        categoryColumn.setCellValueFactory(tariff ->
                new SimpleStringProperty(tariff.getValue().getCategoryName()));

        dailyPriceColumn.setCellValueFactory(tariff ->
                new SimpleStringProperty(formatToCurrency(tariff.getValue().getDailyPrice())));

        weeklyPriceColumn.setCellValueFactory(tariff ->
                new SimpleStringProperty(formatToCurrency(tariff.getValue().getWeeklyPrice())));

        kmPriceColumn.setCellValueFactory(tariff ->
                new SimpleStringProperty(formatToCurrency(tariff.getValue().getMileagePrice())));

        freeKmPriceColumn.setCellValueFactory(tariff ->
                new SimpleStringProperty(formatToCurrency(tariff.getValue().getFreeMileagePrice())));

        fromDateColumn.setCellValueFactory(tariff ->
            new SimpleStringProperty(formatLocalDate(tariff.getValue().getFromDate())));

        toDateColumn.setCellValueFactory(tariff ->
                new SimpleStringProperty(formatLocalDate(tariff.getValue().getToDate())));

        // insert users to the table
        DataLoader<CarTariff> dataLoader = new DataLoader<CarTariff>() {
            @Override
            protected Response dataLoadRequest() {
                return FrontController.handleRequest(GET_ALL, CAR_TARIFF, null);
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
