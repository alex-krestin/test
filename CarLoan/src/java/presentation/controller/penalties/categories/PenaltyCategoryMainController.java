package presentation.controller.penalties.categories;


import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.controller.CategoryController;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.PENALTY_CATEGORY;

public class PenaltyCategoryMainController extends CategoryController implements Initializable {

    public void openAddCategory() {
        FrontController.handleRequest("AddPenaltyCategory", "Nuova categoria");
    }

    public void openEditCategory() {
        if (currentCategory != null) {
            FrontController.handleRequest("EditPenaltyCategory", "Modifica categoria");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessuna categoria selezionata!");
        }
    }

    public void deleteCategory() {
        deleteItem(currentCategory, PENALTY_CATEGORY);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // reset current category
        currentCategory = null;
        initializeCategoryView(PENALTY_CATEGORY);
    }
}
