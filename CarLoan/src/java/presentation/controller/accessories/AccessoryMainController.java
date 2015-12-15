package presentation.controller.accessories;

import entity.Accessory;
import entity.Category;
import entity.Response;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.controller.DefaultController;
import presentation.helper.exception.DataLoadingException;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.ACCESSORY;
import static presentation.helper.EntityCode.ACCESSORY_CATEGORY;
import static presentation.helper.OperationCode.GET_ALL;

public class AccessoryMainController extends DefaultController implements Initializable {

    public TableView<Accessory> table;
    public TableColumn<Accessory, String> inventoryCodeColumn;
    public TableColumn<Accessory, String> titleColumn;
    public TableColumn<Accessory, String> descriptionColumn;
    public TableColumn<Accessory, String> categoryColumn;
    public TableColumn<Accessory, String> agencyCodeColumn;
    public TextField title_filter;
    public TextField description_filter;
    public TextField inventory_code_filter;
    public TextField agency_code_filter;
    public ObjectComboBox<Category> fx_category;

    static Accessory currentAccessory;
    private final ObservableList<Accessory> accessoryList = FXCollections.observableArrayList();
    private SortedList<Accessory> sortedData;

    public void gotoCategories() {
        FrontController.handleRequest("AccessoryCategories");
    }

    public void openAddAccessory() {
        if (fx_category.getItems().size() > 1) {
            FrontController.handleRequest("AddAccessory", "Nuovo accessorio");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Prima di procedere è neccessario " +
                    "aggiungere almeno una categoria!");
        }
    }

    public void openEditAccessory() {
        if (currentAccessory != null) {
            FrontController.handleRequest("EditAccessory", "Modifica accessorio");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessun accessorio selezionato!");
        }
    }

    public void deleteAccessory() {
        deleteItem(currentAccessory, ACCESSORY);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // reset currentUser every time UsersView is loaded
        currentAccessory = null;

        fx_category.loadValues(ACCESSORY_CATEGORY);
        Category showAll = new Category.Builder("Tutti").build();
        fx_category.getItems().add(0, showAll);
        fx_category.getSelectionModel().selectFirst();

        // load all users from the database
        getAllAccessories();

        // set table columns
        inventoryCodeColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryCode"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryColumn.setCellValueFactory(accessory -> new SimpleStringProperty(accessory.getValue().getCategoryName()));
        agencyCodeColumn.setCellValueFactory(accessory -> new SimpleStringProperty(accessory.getValue().getCurrentAgencyCode()));

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
        // wrap the ObservableList in a FilteredList (initially display all accessories)
        FilteredList<Accessory> filteredByTitle = new FilteredList<>(accessoryList, showAll -> true);

        // set the filter Predicate whenever the filter changes
        title_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByTitle.setPredicate(accessory -> {
                // Compare first name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return accessory.getTitle().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // update custom filter
        FilteredList<Accessory> customFilter = filteredByTitle;
        FilteredList<Accessory> filteredByDescription = new FilteredList<>(customFilter, showAll -> true);

        description_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByDescription.setPredicate(accessory -> {
                // Compare last name of every user with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return accessory.getDescription().toLowerCase().contains(lowerCaseFilter);
            });

        });

        customFilter = filteredByDescription;
        FilteredList<Accessory> filteredByInventoryCode = new FilteredList<>(customFilter, showAll -> true);

        inventory_code_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByInventoryCode.setPredicate(accessory -> {
                // Compare username of every user with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return accessory.getInventoryCode().toLowerCase().contains(lowerCaseFilter);
            });

        });

        customFilter = filteredByInventoryCode;
        FilteredList<Accessory> filteredByAgencyCode = new FilteredList<>(customFilter, showAll -> true);

        agency_code_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByAgencyCode.setPredicate(accessory -> {
                // Compare agency code of every user with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return accessory.getCurrentAgencyCode().toLowerCase().contains(lowerCaseFilter);
            });

        });

        customFilter = filteredByAgencyCode;
        FilteredList<Accessory> filteredByCategory = new FilteredList<>(customFilter, showAll -> true);

        addCategoryFilter(fx_category, filteredByCategory);

        customFilter = filteredByCategory;

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
                currentAccessory = null;
                return; // invalid data
            }

            // get selected user
            // update current user
            currentAccessory = sortedData.get(rowIndex);

        }
    }


    public void resetFilters() {
        resetTextFields(title_filter, description_filter, inventory_code_filter, agency_code_filter);
        resetComboBoxes(fx_category);
    }

    private void getAllAccessories() {
        // start displaying the loading indicator at the Application Thread
        spinnerBox.setVisible(true);

        // loads the items at another thread, asynchronously
        Task<Boolean> dataLoader = new Task<Boolean>() {
            {
                setOnSucceeded(workerStateEvent -> {
                    spinnerBox.setVisible(false); // stop displaying the loading indicator
                });
                setOnFailed(workerStateEvent -> {
                    spinnerBox.setVisible(false); // stop displaying the loading indicator
                    AlertHandler.showException("Errore di caricamento dati", new DataLoadingException());
                });
            }

            @Override
            protected Boolean call() throws Exception {
                // request all users from the database
                Response response = FrontController.handleRequest(GET_ALL, ACCESSORY, null);
                // extract list of objects <User> from TransferObject
                List<?> accessories = response.getList();

                // add all users to ObservableList
                for(Object object : accessories) {
                    Accessory accessory = (Accessory) object;
                    accessoryList.add(accessory);
                }

                return true;
            }
        };

        Thread loadingThread = new Thread(dataLoader, "data-loader");
        loadingThread.setDaemon(true);
        loadingThread.start();
    }

}
