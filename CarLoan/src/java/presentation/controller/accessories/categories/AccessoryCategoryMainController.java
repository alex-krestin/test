package presentation.controller.accessories.categories;


import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.controller.CategoryController;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.ACCESSORY_CATEGORY;

public class AccessoryCategoryMainController extends CategoryController implements Initializable {

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
        deleteItem(currentCategory, ACCESSORY_CATEGORY);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // reset current category
        currentCategory = null;
        initializeCategoryView(ACCESSORY_CATEGORY);
    }
}
