package presentation.controller.agencies;

import entity.Agency;
import entity.TransferObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.controller.DefaultController;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.AGENCY;
import static presentation.helper.OperationCode.DELETE;
import static presentation.helper.OperationCode.GET_ALL;


public class AgencyMainController extends DefaultController implements Initializable {

    //region FXML Agency's TableView
    
    public TableView<Agency> table;
    public TableColumn<Agency, String> agency_code_column;
    public TableColumn<Agency, String> city_column;
    public TableColumn<Agency, String> address_column;
    public TableColumn<Agency, String> phone_column;
    public TableColumn<Agency, String> fax_column;
    public TableColumn<Agency, String> email_column;

    //endregion

    //region FXML Filters
    public TextField city_filter;
    public TextField agency_code_filter;
 //   public Button clear;
    //endregion

    protected static Agency currentAgency;
    private final ObservableList<Agency> agencyList = FXCollections.observableArrayList();
    private SortedList<Agency> sortedData;

    //region Toolbar Management
    /* Manage all FXML buttons on the toolbar */

    public void openAddAgency() {
        FrontController.handleRequest("AddAgency", "Nuova agenzia");
    }

    public void openEditAgency() {
        if (currentAgency != null) {
            FrontController.handleRequest("EditAgency", "Aggiorna agenzia");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessuna agenzia selezionata!");
        }
    }

    public void deleteAgency() {
        if (currentAgency != null) {
            Boolean choice = AlertHandler.showAlertConfirmationDialog("Cancellazione agenzia",
                    "Vuoi davvero cancellare agenzia", "I dati non potranno essere ripristinati.",
                    "Cancella");

            if (choice) {
                TransferObject responce = FrontController.handleRequest(DELETE, AGENCY, currentAgency);

                if (responce.getOperationResult()) {
                    FrontController.handleRequest("Agencies");
                }
            }
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessuna agenzia selezionata!");
        }
    }
    //endregion

    //region Main Controller Management
    // Methods related to the main screen of AgencyMainController
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // reset currentAgency every time AgenciesView is loaded
        currentAgency = null;
        // load all agencies from the database
        getAgenciesList();

        // set table columns
        agency_code_column.setCellValueFactory(new PropertyValueFactory<>("agencyCode"));
        city_column.setCellValueFactory(new PropertyValueFactory<>("city"));
        address_column.setCellValueFactory(new PropertyValueFactory<>("address"));
        phone_column.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        fax_column.setCellValueFactory(new PropertyValueFactory<>("faxNumber"));
        email_column.setCellValueFactory(new PropertyValueFactory<>("email"));

        // prepare agency's list that can be filtered
        setSortedData();

        // insert users to the table
        table.setItems(sortedData);

        // add listener to the table rows
        table.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());
    }


    private void getAgenciesList() {
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
                TransferObject response = FrontController.handleRequest(GET_ALL, AGENCY, null);
                // extract list of objects <Agency> from TransferObject
                ArrayList<?> agencies = response.getArrayList();

                // add all agencies to ObservableList
                for(Object object : agencies) {
                    Agency agency = (Agency) object;
                    agencyList.add(agency);
                }

                return true;
            }
        };

        Thread loadingThread = new Thread(dataLoader, "data-loader");
        loadingThread.setDaemon(true);
        loadingThread.start();
    }

    private void setSortedData() {
        // create custom filtered list
        FilteredList<Agency> customFilter;

        // wrap the ObservableList in a FilteredList (initially display all users)
        FilteredList<Agency> filteredByCity = new FilteredList<>(agencyList, agency -> true);

        // set the filter Predicate whenever the filter changes
        city_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByCity.setPredicate(agency -> {
                // Compare city of every agency with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return agency.getCity().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // update custom filter
        customFilter = filteredByCity;
        FilteredList<Agency> filteredByAgencyCode = new FilteredList<>(customFilter, showAll -> true);

        agency_code_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByAgencyCode.setPredicate(agency -> {
                // Compare agency code of every agency with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return agency.getAgencyCode().toLowerCase().contains(lowerCaseFilter);
            });
        });

        customFilter = filteredByAgencyCode;

        // wrap the FilteredList in a SortedList.
        sortedData = new SortedList<>(customFilter);

        // bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(table.comparatorProperty());
    }

    private class RowSelectChangeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            int rowIndex = (int) newValue;

            if ((rowIndex == sortedData.size() || rowIndex == -1)) {
                // deselect rows to prevent ArrayIndexOutOfBoundsException
                table.getSelectionModel().clearSelection();
                // reset currentAgency
                currentAgency = null;
                return; // invalid data
            }

            // update current agency to selected
            currentAgency = sortedData.get(rowIndex);
        }
    }

    public void resetFilters() {
        city_filter.clear();
        agency_code_filter.clear();
    }

    //endregion
}
