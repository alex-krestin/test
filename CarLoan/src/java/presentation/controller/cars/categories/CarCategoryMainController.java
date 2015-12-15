package presentation.controller.cars.categories;


import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.controller.CategoryController;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.CAR_CATEGORY;

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
