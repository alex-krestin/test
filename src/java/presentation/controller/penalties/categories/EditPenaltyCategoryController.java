package presentation.controller.penalties.categories;

import entity.Category;
import entity.TransferObject;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.PENALTY_CATEGORY;
import static presentation.helper.OperationCode.UPDATE;


public class EditPenaltyCategoryController extends PenaltyCategoryPopupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_title.setText(currentCategory.getName());
        fx_description.setText(currentCategory.getDescription());
    }

    public void handleEditServiceAction() {
        if (validateInput()) {
            String title = fx_title.getText();
            String description = fx_description.getText();

            Category category = new Category(currentCategory.getId(), title, description);

            // Update service only if there are any changes
            if(!category.equals(currentCategory)) {
                TransferObject response = FrontController.handleRequest(UPDATE, PENALTY_CATEGORY, category);

                if(response.getOperationResult()) {
                    closeWindow();
                    AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Categoria \u00E8 succesivamente aggiornata.");
                    // Update Agencies view
                    FrontController.handleRequest("PenaltyCategories");
                }
            }
            else {
                closeWindow();
            }
        }
    }
}
