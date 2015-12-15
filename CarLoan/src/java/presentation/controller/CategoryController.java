package presentation.controller;


import entity.Category;
import entity.Response;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.helper.EntityCode;
import presentation.helper.exception.DataLoadingException;
import presentation.validation.GridValidator;
import utility.SessionData;

import static presentation.helper.OperationCode.ADD;
import static presentation.helper.OperationCode.GET_ALL;
import static presentation.validation.ValidationConstraint.NOT_EMPTY;

public class CategoryController extends DefaultController {
    public TableView<Category> table;
    public TableColumn<Category, String> titleColumn;
    public TableColumn<Category, String> descriptionColumn;
    public Label fx_title_label;
    public TextField fx_title;
    public ImageView fx_title_validation;
    public TextField fx_description;
    public Label fx_error_message;

    private final ObservableList<Category> categoryList = FXCollections.observableArrayList();
    protected static Category currentCategory;
    public GridPane gridPane;

    protected void initializeCategoryView(EntityCode entityCode) {
        // load categories from the database
        loadCategories(entityCode);
        // insert them to the category table
        showCategories();
        // add change listener to the table
        table.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());
    }

    private void loadCategories(EntityCode entityCode) {
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
            protected Boolean call() {
                // request all users from the database
                Response response = FrontController.handleRequest(GET_ALL, entityCode, null);
                // extract list of objects <Agency> from TransferObject
                Iterable<?> categories = response.getList();

                // add all agencies to ObservableList
                for(Object object : categories) {
                    Category category = (Category) object;
                    categoryList.add(category);
                }

                return true;
            }
        };

        Thread loadingThread = new Thread(dataLoader, "category-loader");
        loadingThread.setDaemon(true);
        loadingThread.start();
    }

    private void showCategories() {

        // set table columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // insert categories to the table
        table.setItems(categoryList);
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
            currentCategory = categoryList.get(rowIndex);
        }
    }

}
