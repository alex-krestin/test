package presentation.controller.accessories.tariffs;


import entity.AccessoryTariff;
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
import javafx.scene.control.cell.PropertyValueFactory;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.controller.tariffs.TariffController;

import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.ACCESSORY_TARIFF;
import static presentation.helper.OperationCode.GET_ALL;

public class AccessoryTariffsMainController extends TariffController implements Initializable {
    public TableView<AccessoryTariff> table;
    public TableColumn<AccessoryTariff, String> categoryColumn;
    public TableColumn<AccessoryTariff, String> dailyPriceColumn;
    public TableColumn<AccessoryTariff, String> maxDaysColumn;
    public TableColumn<AccessoryTariff, String> fromDateColumn;
    public TableColumn<AccessoryTariff, String> toDateColumn;

    static AccessoryTariff currentTariff = null;
    private final ObservableList<AccessoryTariff> tariffList = FXCollections.observableArrayList();

    public void openAddAccessoryTariff() {
        if (tariffList.size() > 0) {
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

        // load all agencies from the database
        getAccessoryTariffsList();

        // set table columns
        categoryColumn.setCellValueFactory(tariff -> new SimpleStringProperty(tariff.getValue().getCategoryName()));
        dailyPriceColumn.setCellValueFactory(tariff -> {
            BigDecimal price = tariff.getValue().getDailyPrice();
            if (price == null) price = new BigDecimal(0);
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALIAN);
            return new SimpleStringProperty(currencyFormatter.format(price));
        });

        maxDaysColumn.setCellValueFactory(new PropertyValueFactory<>("maxDays"));

        setFromDateColumn(fromDateColumn);
        setToDateColumn(toDateColumn);

        // insert users to the table
        table.setItems(tariffList);

        // add listener to the table rows
        table.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());
    }

    private void getAccessoryTariffsList() {
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
                Response response = FrontController.handleRequest(GET_ALL, ACCESSORY_TARIFF, null);
                // extract list of objects <Agency> from TransferObject
                List<?> tariffs = response.getList();

                // add all agencies to ObservableList
                for(Object object : tariffs) {
                    AccessoryTariff tariff = (AccessoryTariff) object;
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
