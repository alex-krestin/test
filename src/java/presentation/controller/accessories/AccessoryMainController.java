package presentation.controller.accessories;

import entity.Accessory;
import entity.Agency;
import entity.Category;
import entity.TransferObject;
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
import javafx.util.Callback;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.controller.DefaultController;
import presentation.helper.exception.DataLoadingException;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.*;
import static presentation.helper.OperationCode.DELETE;
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
    public ComboBox<Category> fx_category;

    protected static Accessory currentAccessory;
    private ObservableList<Accessory> accessoryList = FXCollections.observableArrayList();
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
        if (currentAccessory != null) {
            Boolean choice = AlertHandler.showAlertConfirmationDialog("Cancellazione accessorio",
                    "Vuoi davvero cancellare accessorio", "I dati non potranno essere ripristinati.",
                    "Cancella");

            if (choice) {
                TransferObject responce = FrontController.handleRequest(DELETE, ACCESSORY, currentAccessory);

                if (responce.getOperationResult()) {
                    FrontController.handleRequest("Accessories");
                }
            }
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessun accessorio selezionato!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // reset currentUser every time UsersView is loaded
        currentAccessory = null;

        setCategoriesList();
        Category showAll = new Category("Tutti", null);
        fx_category.getItems().add(0, showAll);
        fx_category.getSelectionModel().selectFirst();

        // load all users from the database
        getAllAccessories();

        // set table columns
        inventoryCodeColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryCode"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryColumn.setCellValueFactory(accessory -> new SimpleStringProperty(accessory.getValue().getCategory().getName()));
        agencyCodeColumn.setCellValueFactory(accessory -> new SimpleStringProperty(accessory.getValue().getAgency().getAgencyCode()));

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
        FilteredList<Accessory> customFilter;

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
        customFilter = filteredByTitle;
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
                return accessory.getAgency().getAgencyCode().toLowerCase().contains(lowerCaseFilter);
            });

        });

        customFilter = filteredByAgencyCode;
        FilteredList<Accessory> filteredByCategory = new FilteredList<>(customFilter, showAll -> true);

        fx_category.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Category defaultCategory = new Category("Tutti", null);

            filteredByCategory.setPredicate(accessory -> {
                String categoryName = newValue.getName();
                if (newValue.equals(defaultCategory)) {
                    categoryName = "";
                }
                return accessory.getCategory().getName().contains(categoryName);
            });
        });

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

    protected void setCategoriesList() {
        TransferObject response = FrontController.handleRequest(GET_ALL, ACCESSORY_CATEGORY, null);
        ArrayList<?> categories = response.getArrayList();

        ObservableList<Category> list = FXCollections.observableArrayList();

        for(Object object : categories) {
            Category category = (Category) object;
            list.add(category);
        }

        fx_category.setItems(list);
        fx_category.getSelectionModel().selectFirst();
        fx_category.setCellFactory(new Callback<ListView<Category>, ListCell<Category>>() {
            @Override
            public ListCell<Category> call(ListView<Category> param) {

                return new ListCell<Category>(){
                    @Override
                    public void updateItem(Category category, boolean empty){
                        super.updateItem(category, empty);
                        if(!empty) {
                            setText(category.toString());
                            setGraphic(null);
                        }
                    }
                };
            }
        });
    }



    public void resetFilters() {
        title_filter.clear();
        description_filter.clear();
        inventory_code_filter.clear();
        agency_code_filter.clear();
        fx_category.getSelectionModel().selectFirst();
    }

    private void getAllAccessories() {
        // start displaying the loading indicator at the Application Thread
        spinnerBox.setVisible(true);

        // loads the items at another thread, asynchronously
        Task dataLoader = new Task<Boolean>() {
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
                TransferObject response = FrontController.handleRequest(GET_ALL, ACCESSORY, null);
                // extract list of objects <User> from TransferObject
                ArrayList<?> accessories = response.getArrayList();

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
