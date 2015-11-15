package presentation.controller.accessories.categories;


import entity.Category;
import entity.TransferObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.controller.DefaultController;
import presentation.helper.exception.DataLoadingException;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.ACCESSORY_CATEGORY;
import static presentation.helper.OperationCode.DELETE;
import static presentation.helper.OperationCode.GET_ALL;

public class AccessoryCategoryMainController extends DefaultController implements Initializable {
    public TableView<Category> table;
    public TableColumn<Category, String> titleColumn;
    public TableColumn<Category, String> descriptionColumn;

    private final ObservableList<Category> categoryList = FXCollections.observableArrayList();
    protected static Category currentCategory;

    public void openAddCategory() {
        FrontController.handleRequest("AddAccessoryCategory", "Nuova categoria");
    }

    public void openEditCategory() {
        if (currentCategory != null) {
            FrontController.handleRequest("EditAccessoryCategory", "Modifica categoria");
        }
        else {
            AlertHandler.showAlert(AlertType.WARNING, "Attenzione", "Nessuna categoria selezionata!");
        }
    }

    public void deleteCategory() {
        if (currentCategory != null) {
            Boolean choice = AlertHandler.showAlertConfirmationDialog("Cancellazione categoria",
                    "Vuoi davvero cancellare categoria", "I dati non potranno essere ripristinati.",
                    "Cancella");

            if (choice) {
                TransferObject responce = FrontController.handleRequest(DELETE, ACCESSORY_CATEGORY, currentCategory);

                if (responce.getOperationResult()) {
                    FrontController.handleRequest("AccessoryCategories");
                }
            }
        }
        else {
            AlertHandler.showAlert(AlertType.WARNING, "Attenzione", "Nessuna categoria selezionata!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentCategory = null;

        // load categories from the database
        getCategoryList();

        // set table columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // insert categories to the table
        table.setItems(categoryList);

        // add listener to the table rows
        table.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());
    }

    private void getCategoryList() {
        /*
        if (spinnerBox.isVisible()) {
            return;
        }
*/
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
                TransferObject response = FrontController.handleRequest(GET_ALL, ACCESSORY_CATEGORY, null);
                // extract list of objects <Agency> from TransferObject
                Iterable<?> categories = response.getArrayList();

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

    private class RowSelectChangeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            int rowIndex = (int) newValue;

            // update current service to selected
            currentCategory = categoryList.get(rowIndex);
        }
    }
}
