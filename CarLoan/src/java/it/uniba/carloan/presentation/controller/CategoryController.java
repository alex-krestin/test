package it.uniba.carloan.presentation.controller;


import it.uniba.carloan.business.utility.SessionData;
import it.uniba.carloan.entity.Category;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.FrontController;
import it.uniba.carloan.presentation.helper.EntityCode;
import it.uniba.carloan.presentation.validation.GridValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import static it.uniba.carloan.presentation.helper.OperationCode.ADD;
import static it.uniba.carloan.presentation.helper.OperationCode.GET_ALL;
import static it.uniba.carloan.presentation.validation.ValidationConstraint.NOT_EMPTY;

public class CategoryController extends DefaultController {
    public TableView<Category> table;
    public TableColumn<Category, String> titleColumn;
    public TableColumn<Category, String> descriptionColumn;
    public Label fx_title_label;
    public TextField fx_title;
    public TextField fx_description;

    private ObservableList<Category> loadedData = FXCollections.observableArrayList();
    protected static Category currentCategory;
    public GridPane gridPane;

    protected void initializeCategoryView(EntityCode entityCode) {
        currentCategory = null;

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // load categories from the database

        DataLoader<Category> dataLoader = new DataLoader<Category>() {
            @Override
            protected Response dataLoadRequest() {
                return FrontController.handleRequest(GET_ALL, entityCode, null);
            }

            @Override
            protected HBox spinnerBox() {
                return spinnerBox;
            }
        };

        loadedData = dataLoader.getLoadedData();
        // insert categories to the table
        table.setItems(loadedData);
        // add change listener to the table
        table.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());
    }


    @SuppressWarnings("LawOfDemeter")
    private boolean validateInput() {
        GridValidator gv = new GridValidator(gridPane);
        gv.addConstraint(NOT_EMPTY).validate(fx_title);

        return gv.isValid();
    }

    @SuppressWarnings("LawOfDemeter")
    protected void addCategory(EntityCode entityCode) {
        if (validateInput()) {
            String title = fx_title.getText();
            String description = fx_description.getText();

            Category category = new Category.Builder(title).description(description).build();

            Response response = FrontController.handleRequest(ADD, entityCode, category);

            if(response.getOperationResult()) {
                closeWindow();
                AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Categoria \u00E8 aggiunta correttamente.");
                // Update Services view
                FrontController.handleRequest(SessionData.currentRootView);
            }
        }
    }

    @SuppressWarnings("LawOfDemeter")
    protected void editCategory(Category currentCategory, EntityCode entityCode) {
        if (validateInput()) {
            String title = fx_title.getText();
            String description = fx_description.getText();

            Category category = new Category.Builder(title)
                    .id(currentCategory.getId())
                    .description(description).build();

            executeUpdate(currentCategory, category, entityCode);
        }
    }

    private class RowSelectChangeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            int rowIndex = (int) newValue;

            // update current service to selected
            currentCategory = loadedData.get(rowIndex);
        }
    }

}
