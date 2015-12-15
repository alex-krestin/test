package presentation.controller.services.tariffs;


import entity.Response;
import entity.ServiceTariff;
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

import static presentation.helper.EntityCode.SERVICE_TARIFF;
import static presentation.helper.OperationCode.GET_ALL;

public class ServiceTariffsMainController extends TariffController implements Initializable {
    public TableView<ServiceTariff> table;
    public TableColumn<ServiceTariff, String> serviceColumn;
    public TableColumn<ServiceTariff, String> priceColumn;
    public TableColumn<ServiceTariff, String> fromDateColumn;
    public TableColumn<ServiceTariff, String> toDateColumn;

    static ServiceTariff currentTariff;
    private final ObservableList<ServiceTariff> tariffList = FXCollections.observableArrayList();

    public void openAddServiceTariff() {
        if (tariffList.size() > 0) {
            FrontController.handleRequest("AddServiceTariff", "Nuovo tariffo");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Prima di procedere è neccessario " +
                    "aggiungere almeno una penale!");
        }
    }

    public void openEditServiceTariff() {
        openEditTariff(currentTariff, "Service");
    }

    public void deleteServiceTariff() {
        deleteItem(currentTariff, SERVICE_TARIFF);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // reset currentAgency every time AgenciesView is loaded
        currentTariff = null;

        // load all agencies from the database
        getAccessoryTariffsList();

        // set table columns
        serviceColumn.setCellValueFactory(tariff -> new SimpleStringProperty(tariff.getValue().getServiceName()));
        priceColumn.setCellValueFactory(tariff -> {
            BigDecimal price = tariff.getValue().getPrice();
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
                Response response = FrontController.handleRequest(GET_ALL, SERVICE_TARIFF, null);
                // extract list of objects <Agency> from TransferObject
                List<?> tariffs = response.getList();

                // add all agencies to ObservableList
                for(Object object : tariffs) {
                    ServiceTariff tariff = (ServiceTariff) object;
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
