package it.uniba.carloan.presentation.controller.agencies;

import it.uniba.carloan.entity.Agency;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.FrontController;
import it.uniba.carloan.presentation.controller.DataLoader;
import it.uniba.carloan.presentation.controller.DefaultController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.AGENCY;
import static it.uniba.carloan.presentation.helper.OperationCode.GET_ALL;


public class AgencyMainController extends DefaultController implements Initializable {

    public TableView<Agency> table;
    public TableColumn<Agency, String> agency_code_column;
    public TableColumn<Agency, String> city_column;
    public TableColumn<Agency, String> address_column;
    public TableColumn<Agency, String> phone_column;
    public TableColumn<Agency, String> fax_column;
    public TableColumn<Agency, String> email_column;

    public TextField city_filter;
    public TextField agency_code_filter;

    static Agency currentAgency;
    private ObservableList<Agency> loadedData = FXCollections.observableArrayList();
    private SortedList<Agency> sortedData;

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
        deleteItem(currentAgency, AGENCY);
    }
    //endregion


    // Methods related to the main screen of AgencyMainController
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // reset currentAgency every time AgenciesView is loaded
        currentAgency = null;

        // set table columns
        agency_code_column.setCellValueFactory(new PropertyValueFactory<>("agencyCode"));
        city_column.setCellValueFactory(new PropertyValueFactory<>("city"));
        address_column.setCellValueFactory(new PropertyValueFactory<>("address"));
        phone_column.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        fax_column.setCellValueFactory(new PropertyValueFactory<>("faxNumber"));
        email_column.setCellValueFactory(new PropertyValueFactory<>("email"));

        DataLoader<Agency> dataLoader = new DataLoader<Agency>() {
            @Override
            protected Response dataLoadRequest() {
                return FrontController.handleRequest(GET_ALL, AGENCY, null);
            }

            @Override
            protected HBox spinnerBox() {
                return spinnerBox;
            }
        };

        loadedData = dataLoader.getLoadedData();

        // prepare agency's list that can be filtered
        setSortedData();

        // insert users to the table
        table.setItems(sortedData);

        // add listener to the table rows
        table.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());
    }


    private void setSortedData() {
        // create custom filtered list

        // wrap the ObservableList in a FilteredList (initially display all users)
        FilteredList<Agency> filteredByCity = new FilteredList<>(loadedData, agency -> true);

        // set the filter Predicate whenever the filter changes
        city_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByCity.setPredicate(agency -> {
                // Compare city of every agency with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return agency.getCity().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // update custom filter
        FilteredList<Agency> customFilter = filteredByCity;
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
        resetTextFields(city_filter, agency_code_filter);
    }

    //endregion
}
