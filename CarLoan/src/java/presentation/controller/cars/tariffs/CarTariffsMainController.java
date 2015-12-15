package presentation.controller.cars.tariffs;


import entity.CarTariff;
import entity.Response;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.controller.tariffs.TariffController;

import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.CAR_TARIFF;
import static presentation.helper.OperationCode.GET_ALL;

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
    private final ObservableList<CarTariff> tariffList = FXCollections.observableArrayList();


    public void openAddCarTariff() {
        if (tariffList.size() > 0) {
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

        // load all agencies from the database
        getCarTariffsList();

        // set table columns
        categoryColumn.setCellValueFactory(tariff -> new SimpleStringProperty(tariff.getValue().getCategoryName()));
        dailyPriceColumn.setCellValueFactory(tariff -> {
            BigDecimal price = tariff.getValue().getDailyPrice();
            if (price == null) price = new BigDecimal(0);
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALIAN);
            return new SimpleStringProperty(currencyFormatter.format(price));
        });
        weeklyPriceColumn.setCellValueFactory(tariff -> {
            BigDecimal price = tariff.getValue().getWeeklyPrice();
            if (price == null) price = new BigDecimal(0);
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALIAN);
            return new SimpleStringProperty(currencyFormatter.format(price));
        });
        kmPriceColumn.setCellValueFactory(tariff -> {
            BigDecimal price = tariff.getValue().getMileagePrice();
            if (price == null) price = new BigDecimal(0);
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALIAN);
            return new SimpleStringProperty(currencyFormatter.format(price));
        });
        freeKmPriceColumn.setCellValueFactory(tariff -> {
            BigDecimal price = tariff.getValue().getFreeMileagePrice();
            if (price == null) price = new BigDecimal(0);
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALIAN);
            return new SimpleStringProperty(currencyFormatter.format(price));
        });

        setFromDateColumn(fromDateColumn);
        setToDateColumn(toDateColumn);

        // insert users to the table
        table.setItems(tariffList);

        // add listener to the table rows
        table.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());
    }

    private void getCarTariffsList() {
        if (spinnerBox.isVisible()) {
            return;
        }

        // start displaying the loading indicator at the Application Thread
        spinnerBox.setVisible(true);

        // loads the items at another thread, asynchronously
        Task dataLoader = new Task<Boolean>() {
            {
                setOnSucceeded(workerStateEvent -> {
                    spinnerBox.setVisible(false); // stop displaying the loading indicator
                });
                //setOnFailed(workerStateEvent -> getException().printStackTrace());
            }

            @Override
            protected Boolean call() throws Exception {
                // request all users from the database
                Response response = FrontController.handleRequest(GET_ALL, CAR_TARIFF, null);
                // extract list of objects <Agency> from TransferObject
                List<?> tariffs = response.getList();

                // add all agencies to ObservableList
                for(Object object : tariffs) {
                    CarTariff tariff = (CarTariff) object;
                    tariffList.add(tariff);
                }

                return true;
            }
        };

        Thread loadingThread = new Thread(dataLoader, "data-loader");
        loadingThread.setDaemon(true);
        loadingThread.start();
    }

    private class RowSelectChangeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            int rowIndex = (int) newValue;
            // update current user
            currentTariff = tariffList.get(rowIndex);
        }
    }


}
