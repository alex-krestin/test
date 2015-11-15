package presentation.controller.penalties.categories;

import entity.Category;
import entity.TransferObject;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;
import utility.SessionData;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.PENALTY_CATEGORY;
import static presentation.helper.OperationCode.ADD;


public class AddPenaltyCategoryController extends PenaltyCategoryPopupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleSaveCategoryAction() {
        if (validateInput()) {
            String title = fx_title.getText();
            String description = fx_description.getText();

            Category category = new Category(title, description);

            TransferObject response = FrontController.handleRequest(ADD, PENALTY_CATEGORY, category);

            if(response.getOperationResult()) {
                closeWindow();
                AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Categoria \u00E8 aggiunta correttamente.");
                // Update Services view
                FrontController.handleRequest("PenaltyCategories");
            }
        }
    }
}
