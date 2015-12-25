package it.uniba.carloan.presentation.controller.cars.categories;


import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.FrontController;
import it.uniba.carloan.presentation.controller.CategoryController;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.CAR_CATEGORY;

public class CarCategoryMainController extends CategoryController implements Initializable {

    public void openAddCategory() {
        FrontController.handleRequest("AddCarCategory", "Nuova classe");
    }

    public void openEditCategory() {
        if (currentCategory != null) {
            FrontController.handleRequest("EditCarCategory", "Modifica classe");
        }
        else {
            AlertHandler.showAlert(AlertType.WARNING, "Attenzione", "Nessuna classe selezionata!");
        }
    }

    public void deleteCategory() {
        deleteItem(currentCategory, CAR_CATEGORY);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // reset current category
        currentCategory = null;
        initializeCategoryView(CAR_CATEGORY);
    }

}
