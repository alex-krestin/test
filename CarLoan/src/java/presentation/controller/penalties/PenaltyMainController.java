package presentation.controller.penalties;


import entity.Category;
import entity.Penalty;
import entity.Response;
import javafx.application.Platform;
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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.PENALTY;
import static presentation.helper.EntityCode.PENALTY_CATEGORY;
import static presentation.helper.OperationCode.GET_ALL;

public class PenaltyMainController extends DefaultController implements Initializable {


    public TableView<Penalty> table;
    public TableColumn<Penalty, String> titleColumn;
    public TableColumn<Penalty, String> descriptionColumn;
    public TableColumn<Penalty, String> categoryColumn;
    public TextField title_filter;
    public ObjectComboBox<Category> fx_category;

    static Penalty currentPenalty;
    private final ObservableList<Penalty> penaltyList = FXCollections.observableArrayList();
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
        deleteItem(currentPenalty, PENALTY);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_category.loadValues(PENALTY_CATEGORY);

        Category showAll = new Category.Builder("Tutti").build();
        fx_category.getItems().add(0, showAll);
        fx_category.getSelectionModel().selectFirst();


        // reset currentUser every time UsersView is loaded
        currentPenalty = null;

        // load all users from the database
        getAllPenalties();

        // set table columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryColumn.setCellValueFactory(penalty -> new SimpleStringProperty(penalty.getValue().getCategoryName()));

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
        FilteredList<Penalty> customFilter = filteredByTitle;
        FilteredList<Penalty> filteredByCategory = new FilteredList<>(customFilter, showAll -> true);

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
                currentPenalty = null;
                return; // invalid data
            }

            // update current user
            currentPenalty = sortedData.get(rowIndex);
        }
    }

    public void resetFilters() {
        resetTextFields(title_filter);
        resetComboBoxes(fx_category);
    }

    private void getAllPenalties() {

        spinnerBox.setVisible(true);

        // loads the items at another thread, asynchronously
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // request all users from the database
                    Response response = FrontController.handleRequest(GET_ALL, PENALTY, null);
                    // extract list of objects <User> from TransferObject
                    List<?> penalties = response.getList();

                    // add all users to ObservableList
                    for(Object object : penalties) {
                        Penalty penalty = (Penalty) object;
                        penaltyList.add(penalty);
                    }
                } finally {
                    // just updates the list view items at the
                    // Application Thread
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            spinnerBox.setVisible(false); // stop displaying the loading indicator
                        }
                    });
                }
            }
        }).start();
    }

    /*
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
                Response response = FrontController.handleRequest(GET_ALL, PENALTY, null);
                // extract list of objects <User> from TransferObject
                List<?> penalties = response.getList();

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
    */

}
