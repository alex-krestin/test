package it.uniba.carloan.presentation.controller.clients;

import it.uniba.carloan.entity.Client;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.FrontController;
import it.uniba.carloan.presentation.controller.DataLoader;
import it.uniba.carloan.presentation.controller.DefaultController;
import javafx.beans.property.SimpleStringProperty;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.CLIENT;
import static it.uniba.carloan.presentation.helper.OperationCode.GET_ALL;


public class ClientMainController extends DefaultController implements Initializable {

    public TableView<Client> table;
    public TableColumn<Client, String> nameColumn;
    public TableColumn<Client, String> surnameColumn;
    public TableColumn<Client, String> sexColumn;
    public TableColumn<Client, String> fiscalCodeColumn;
    public TableColumn<Client, String> birthdayColumn;
    public TableColumn<Client, String> phoneColumn;
    public TableColumn<Client, String> commentColumn;
    public TextField name_filter;
    public TextField surname_filter;
    public TextField fiscal_code_filter;
    public TextField phone_filter;


    static Client currentClient;
    private ObservableList<Client> loadedData = FXCollections.observableArrayList();
    private SortedList<Client> sortedData;

    public void openAddClient() {
        FrontController.handleRequest("AddClient", "Nuovo cliente");
    }

    public void openEditClient() {
        if (currentClient != null) {
            FrontController.handleRequest("EditClient", "Modifica cliente");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessun cliente selezionato!");
        }
    }

    public void deleteClient() {
        deleteItem(currentClient, CLIENT);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // reset currentUser every time UsersView is loaded
        currentClient = null;

        // set table columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
        fiscalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        birthdayColumn.setCellValueFactory(client -> {
            LocalDate date = client.getValue().getBirthday();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String birthday = date.format(format);
            return new SimpleStringProperty(birthday);
        });

        DataLoader<Client> dataLoader = new DataLoader<Client>() {
            @Override
            protected Response dataLoadRequest() {
                return FrontController.handleRequest(GET_ALL, CLIENT, null);
            }

            @Override
            protected HBox spinnerBox() {
                return spinnerBox;
            }
        };

        loadedData = dataLoader.getLoadedData();

        // prepare user's list that can be filtered
        setSortedData();

        // bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        // insert users to the table
        table.setItems(sortedData);

        // add listener to the table rows
        table.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());
    }


    private void setSortedData() {
        // create custom filtered list

        // wrap the ObservableList in a FilteredList (initially display all accessories)
        FilteredList<Client> filteredByName = new FilteredList<>(loadedData, showAll -> true);

        // set the filter Predicate whenever the filter changes
        name_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByName.setPredicate(client -> {
                // Compare first name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return client.getName().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // update custom filter
        FilteredList<Client> customFilter = filteredByName;
        FilteredList<Client> filteredBySurname = new FilteredList<>(customFilter, showAll -> true);

        surname_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredBySurname.setPredicate(client -> {
                // Compare last name of every user with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return client.getSurname().toLowerCase().contains(lowerCaseFilter);
            });
        });

        customFilter = filteredBySurname;
        FilteredList<Client> filteredByFiscalCode = new FilteredList<>(customFilter, showAll -> true);

        fiscal_code_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByFiscalCode.setPredicate(client -> {
                // Compare username of every user with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return client.getFiscalCode().toLowerCase().contains(lowerCaseFilter);
            });
        });

        customFilter = filteredByFiscalCode;
        FilteredList<Client> filteredByPhone = new FilteredList<>(customFilter, showAll -> true);

        phone_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByPhone.setPredicate(client -> {
                // Compare agency code of every user with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return client.getPhoneNumber().toLowerCase().contains(lowerCaseFilter);
            });
        });

        customFilter = filteredByPhone;

        // wrap the FilteredList in a SortedList.
        sortedData = new SortedList<>(customFilter);
    }

    private class RowSelectChangeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            int rowIndex = (int) newValue;

            if ((rowIndex == sortedData.size() || rowIndex == -1)) {
                // deselect rows to prevent ArrayIndexOutOfBoundsException
                table.getSelectionModel().clearSelection();
                // reset currentUser
                currentClient = null;
                return; // invalid data
            }

            // update current user
            currentClient = sortedData.get(rowIndex);
        }
    }

    public void resetFilters() {
        resetTextFields(name_filter, surname_filter, fiscal_code_filter, phone_filter);
    }

}
