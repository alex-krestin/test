package presentation.controller.services;

import entity.Response;
import entity.Service;
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
import presentation.controller.DefaultController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.SERVICE;
import static presentation.helper.OperationCode.GET_ALL;

public class ServiceMainController extends DefaultController implements Initializable{

    public TableView<Service> table;
    public TableColumn<Service, String> titleColumn;
    public TableColumn<Service, String> descriptionColumn;

    private final ObservableList<Service> serviceList = FXCollections.observableArrayList();
    static Service currentService;


    public void openAddService() {
        FrontController.handleRequest("AddService", "Nuovo servizio");
    }

    public void openEditService() {
        if (currentService != null) {
            FrontController.handleRequest("EditService", "Aggiorna servizio");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessun servizio selezionato!");
        }
    }

    public void deleteService() {
        deleteItem(currentService, SERVICE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // load available services from the database
        getServicesList();

        // set table columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // insert services to the table
        table.setItems(serviceList);

        // add listener to the table rows
        table.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());

        /*
        priceColumn.setCellValueFactory(service -> {
            SimpleStringProperty property = new SimpleStringProperty();
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALY); //TODO Generalize
            property.setValue(currencyFormatter.format(service.getValue().getPrice()));
            return property;
        });
        validFromColumn.setCellValueFactory(service -> {
            SimpleStringProperty property = new SimpleStringProperty();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            property.setValue(dateFormat.format(service.getValue().getValidFrom()));
            return property;
        });
        validToColumn.setCellValueFactory(service -> {
            SimpleStringProperty property = new SimpleStringProperty();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            property.setValue(dateFormat.format(service.getValue().getValidTo()));
            return property;
        });
        */
    }


    private void getServicesList() {
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
                Response response = FrontController.handleRequest(GET_ALL, SERVICE, null);
                // extract list of objects <Agency> from TransferObject
                List<?> services = response.getList();

                // add all agencies to ObservableList
                for(Object object : services) {
                    Service service = (Service) object;
                    serviceList.add(service);
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

            if ((rowIndex == serviceList.size() || rowIndex == -1)) {
                // deselect rows to prevent ArrayIndexOutOfBoundsException
                table.getSelectionModel().clearSelection();
                // reset currentService
                currentService = null;
                return; // invalid data
            }

            // update current service to selected
            currentService = serviceList.get(rowIndex);
        }
    }
}

