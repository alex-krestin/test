package it.uniba.carloan.presentation.controller.penalties;


import it.uniba.carloan.entity.Category;
import it.uniba.carloan.entity.Penalty;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.javafx.scene.control.ObjectComboBox;
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
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.PENALTY;
import static it.uniba.carloan.presentation.helper.EntityCode.PENALTY_CATEGORY;
import static it.uniba.carloan.presentation.helper.OperationCode.GET_ALL;

public class PenaltyMainController extends DefaultController implements Initializable {


    public TableView<Penalty> table;
    public TableColumn<Penalty, String> titleColumn;
    public TableColumn<Penalty, String> descriptionColumn;
    public TableColumn<Penalty, String> categoryColumn;
    public TextField title_filter;
    public ObjectComboBox<Category> fx_category;

    static Penalty currentPenalty;
    private ObservableList<Penalty> loadedData = FXCollections.observableArrayList();
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

        // set table columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryColumn.setCellValueFactory(penalty -> new SimpleStringProperty(penalty.getValue().getCategoryName()));

        DataLoader<Penalty> dataLoader = new DataLoader<Penalty>() {
            @Override
            protected Response dataLoadRequest() {
                return FrontController.handleRequest(GET_ALL, PENALTY, null);
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
        FilteredList<Penalty> filteredByTitle = new FilteredList<>(loadedData, showAll -> true);

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

}
