package presentation.controller.penalties;


import entity.Category;
import entity.Penalty;
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
import presentation.helper.EntityCode;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.PENALTY;
import static presentation.helper.EntityCode.PENALTY_CATEGORY;
import static presentation.helper.OperationCode.DELETE;
import static presentation.helper.OperationCode.GET_ALL;

public class PenaltyMainController extends DefaultController implements Initializable {


    public TableView<Penalty> table;
    public TableColumn<Penalty, String> titleColumn;
    public TableColumn<Penalty, String> descriptionColumn;
    public TableColumn<Penalty, String> categoryColumn;
    public TextField title_filter;
    public ComboBox<Category> fx_category;

    protected static Penalty currentPenalty;
    private ObservableList<Penalty> penaltyList = FXCollections.observableArrayList();
    private SortedList<Penalty> sortedData;


    public void gotoCategories() {
        FrontController.handleRequest("PenaltyCategories");
    }

    public void openAddPenalty() {
        if (fx_category.getItems().size() > 1) {
            FrontController.handleRequest("AddPenalty", "Nuova multa");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Prima di procedere è neccessario " +
                    "aggiungere almeno una categoria!");
        }
    }

    public void openEditPenalty() {
        if (currentPenalty != null) {
            FrontController.handleRequest("EditPenalty", "Modifica multa");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessuna multa selezionata!");
        }
    }

    public void deletePenalty() {
        if (currentPenalty != null) {
            Boolean choice = AlertHandler.showAlertConfirmationDialog("Cancellazione multa",
                    "Vuoi davvero cancellare multa", "I dati non potranno essere ripristinati.",
                    "Cancella");

            if (choice) {
                TransferObject responce = FrontController.handleRequest(DELETE, EntityCode.PENALTY, currentPenalty);

                if (responce.getOperationResult()) {
                    FrontController.handleRequest("Penalties");
                }
            }
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessuna multa selezionata!");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCategoriesList();

        Category showAll = new Category("Tutti", null);
        fx_category.getItems().add(0, showAll);
        fx_category.getSelectionModel().selectFirst();


        // reset currentUser every time UsersView is loaded
        currentPenalty = null;

        // load all users from the database
        getAllPenalties();

        // set table columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryColumn.setCellValueFactory(accessory -> new SimpleStringProperty(accessory.getValue().getCategory().getName()));

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
        FilteredList<Penalty> customFilter;

        // wrap the ObservableList in a FilteredList (initially display all accessories)
        FilteredList<Penalty> filteredByTitle = new FilteredList<>(penaltyList, showAll -> true);

        // set the filter Predicate whenever the filter changes
        title_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByTitle.setPredicate(penalty -> {
                // Compare first name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return penalty.getTitle().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // update custom filter
        customFilter = filteredByTitle;
        FilteredList<Penalty> filteredByCategory = new FilteredList<>(customFilter, showAll -> true);

        fx_category.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Category defaultCategory = new Category("Tutti", null);

            filteredByCategory.setPredicate(penalty -> {
                String categoryName = newValue.getName();
                if (newValue.equals(defaultCategory)) {
                    categoryName = "";
                }
                return penalty.getCategory().getName().contains(categoryName);
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
                currentPenalty = null;
                return; // invalid data
            }

            // update current user
            currentPenalty = sortedData.get(rowIndex);
        }
    }

    protected void setCategoriesList() {
        TransferObject response = FrontController.handleRequest(GET_ALL, PENALTY_CATEGORY, null);
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
        fx_category.getSelectionModel().selectFirst();
    }

    private void getAllPenalties() {
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
                TransferObject response = FrontController.handleRequest(GET_ALL, PENALTY, null);
                // extract list of objects <User> from TransferObject
                ArrayList<?> penalties = response.getArrayList();

                // add all users to ObservableList
                for(Object object : penalties) {
                    Penalty penalty = (Penalty) object;
                    penaltyList.add(penalty);
                }

                return true;
            }
        };

        Thread loadingThread = new Thread(dataLoader, "data-loader");
        loadingThread.setDaemon(true);
        loadingThread.start();
    }

}
