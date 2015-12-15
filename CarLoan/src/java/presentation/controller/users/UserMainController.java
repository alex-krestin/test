package presentation.controller.users;

import entity.Agency;
import entity.Response;
import entity.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.css.PseudoClass;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.controller.DefaultController;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.USER;
import static presentation.helper.OperationCode.CHANGE_STATUS;
import static presentation.helper.OperationCode.GET_ALL;


public class UserMainController extends DefaultController implements Initializable {

    //region FXML User's TableView
    public TableView<User> table;
    public TableColumn<User, String> name_column;
    public TableColumn<User, String> surname_column;
    public TableColumn<User, String> username_column;
    public TableColumn<User, String> account_type_column;
    public TableColumn<User, String> agency_code_column;
    //endregion
    //region FXML Filters
    public TextField name_filter;
    public TextField surname_filter;
    public TextField username_filter;
    public TextField agency_code_filter;
    public ComboBox<String> account_type_filter;
    public Button clear;
    //endregion
    //region FXML User's agency information
    public Label city_label;
    public Label address_label;
    public Label tel_label;
    public Label fax_label;
    public Label email_label;
    //endregion

    static Agency currentAgency; // uses in addUser
    static User currentUser; // uses in editUser

    private final ObservableList<User> usersList = FXCollections.observableArrayList();
    private SortedList<User> sortedData;

    //region Toolbar Management
    /* Manage all FXML buttons on the toolbar */

    public void openAddUser() {
        FrontController.handleRequest("AddUser", "Nuovo utente");
    }

    public void openEditUser() {
        if (currentUser != null) {
            FrontController.handleRequest("EditUser", "Aggiorna utente");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessun utente selezionato!");
        }
    }

    public void deleteCurrentUser() {
        deleteItem(currentUser, USER);
    }

    public void blockCurrentUser() {
        if (currentUser != null && currentUser.isAccessGranted()) {
            FrontController.handleRequest(CHANGE_STATUS, USER, currentUser);
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Utente \u00E8 succisivamente bloccato.");
            // Update Users view
            FrontController.handleRequest("Users");
        }
        else if(currentUser == null) {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessun utente selezionato!");
        }
    }

    public void unlockCurrentUser() {
        if (currentUser != null && !currentUser.isAccessGranted()) {
            FrontController.handleRequest(CHANGE_STATUS, USER, currentUser);
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Utente \u00E8 succisivamente sbloccato.");
            // Update Users view
            FrontController.handleRequest("Users");
        }
        else if(currentUser == null) {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessun utente selezionato!");
        }
    }

    //TODO CHANGE THIS SHIT
    public void changeCurrentUserPassword() {
        if (currentUser != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Cambiamento password");
            dialog.setHeaderText(null);
            dialog.setContentText("Inserisci la nuova password:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                //TODO
                //System.out.println("Your name: " + result.get());
            }


            // Update Users view
            FrontController.handleRequest("Users");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessun utente selezionato!");
        }
    }
    //endregion

    //region Main Controller Management
    // Methods related to the main screen of UserMainController

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // reset currentUser every time UsersView is loaded
        currentUser = null;
        // set ComboBox
        account_type_filter.getItems().addAll("Tutti utenti", "Operatori", "Amministratori", "Bloccati");
        account_type_filter.getSelectionModel().selectFirst();

        // load all users from the database
        getAllUsers();

        // set table columns
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname_column.setCellValueFactory(new PropertyValueFactory<>("surname"));
        username_column.setCellValueFactory(new PropertyValueFactory<>("username"));
        account_type_column.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        agency_code_column.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getAgencyCode()));

        // prepare user's list that can be filtered
        setSortedData();

        // bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        // insert users to the table
        table.setItems(sortedData);

        // add listener to the table rows
        table.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());

        // get pseudo class from css
        PseudoClass blockedClass = PseudoClass.getPseudoClass("blocked");

        // update table's row style if user is blocked
        table.setRowFactory(param -> new TableRow<User>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                pseudoClassStateChanged(blockedClass, !empty && !user.isAccessGranted());
            }
        });
    }


    public void resetFilters() {
        resetTextFields(name_filter, surname_filter, username_filter, agency_code_filter);
        resetComboBoxes(account_type_filter);
    }

    private void getAllUsers() {

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
                Response response = FrontController.handleRequest(GET_ALL, USER, null);
                // extract list of objects <User> from TransferObject
                List<?> users = response.getList();

                // add all users to ObservableList
                for(Object object : users) {
                    User user = (User) object;
                    usersList.add(user);
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

        // wrap the ObservableList in a FilteredList (initially display all users)
        FilteredList<User> filteredByName = new FilteredList<>(usersList, user -> true);

        // set the filter Predicate whenever the filter changes
        name_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByName.setPredicate(user -> {
                // Compare first name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return user.getName().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // update custom filter
        FilteredList<User> customFilter = filteredByName;
        FilteredList<User> filteredBySurname = new FilteredList<>(customFilter, showAll -> true);

        surname_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredBySurname.setPredicate(user -> {
                // Compare last name of every user with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return user.getSurname().toLowerCase().contains(lowerCaseFilter);
            });

        });

        customFilter = filteredBySurname;
        FilteredList<User> filteredByUsername = new FilteredList<>(customFilter, showAll -> true);

        username_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByUsername.setPredicate(user -> {
                // Compare username of every user with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return user.getUsername().toLowerCase().contains(lowerCaseFilter);
            });

        });

        customFilter = filteredByUsername;
        FilteredList<User> filteredByAgencyCode = new FilteredList<>(customFilter, showAll -> true);

        agency_code_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByAgencyCode.setPredicate(user -> {
                // Compare agency code of every user with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return user.getAgencyCode().toLowerCase().contains(lowerCaseFilter);
            });

        });

        customFilter = filteredByAgencyCode;
        FilteredList<User> filteredByAccountType = new FilteredList<>(customFilter, showAll -> true);

        account_type_filter.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            filteredByAccountType.setPredicate(user -> {
                // TODO Refactor this in the feature
                String accountType;
                if (Objects.equals(newValue, "Operatori")) {
                    accountType = "OPERATOR";
                }
                else if (Objects.equals(newValue, "Amministratori")) {
                    accountType = "ADMIN";
                }
                else if(Objects.equals(newValue, "Bloccati")) {
                    return !user.isAccessGranted();
                }
                else {
                    accountType = "";
                }
                return user.getAccountType().contains(accountType);
            });
        });

        customFilter = filteredByAccountType;

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
                currentUser = null;
                // reset labels text
                city_label.setText(null);
                address_label.setText(null);
                tel_label.setText(null);
                fax_label.setText(null);
                email_label.setText(null);
                return; // invalid data
            }

            // get selected user
            User user = sortedData.get(rowIndex);
            // update current user
            currentUser = user;

            // show current user's agency information
            city_label.setText(user.getAgencyCity());
            address_label.setText(user.getAgencyAddress());
            tel_label.setText(user.getAgencyPhoneNumber());
            fax_label.setText(user.getAgencyFaxNumber());
            email_label.setText(user.getAgencyEmail());
        }
    }
    //endregion

}
