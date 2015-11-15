package presentation.controller.penalties;

import entity.Category;
import entity.Penalty;
import entity.TransferObject;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.PENALTY;
import static presentation.helper.OperationCode.UPDATE;


public class EditPenaltyController extends PenaltyPopupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCategoriesList();

        fx_title.setText(currentPenalty.getTitle());
        fx_description.setText(currentPenalty.getDescription());
        fx_category.getSelectionModel().select(currentPenalty.getCategory());
    }

    public void handleEditPenaltyAction() {
        if (validateInput()) {

            int id = currentPenalty.getId();
            String title =  fx_title.getText();
            String description = fx_description.getText();
            Category category = fx_category.getValue();

            Penalty penalty = new Penalty(id, title, description, category);

            // Update user only if there are any changes
            if(!penalty.equals(currentPenalty)) {
                System.out.println("Not equal");
                TransferObject response = FrontController.handleRequest(UPDATE, PENALTY, penalty);

                if(response.getOperationResult()) {
                    closeWindow();
                    AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Multa \u00E8 succesivamente aggiornata.");
                    // Update Users view
                    FrontController.handleRequest("Penalties");
                }
            }
            else {
                closeWindow();
            }
        }
    }
}