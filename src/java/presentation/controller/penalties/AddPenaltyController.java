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
import static presentation.helper.OperationCode.ADD;


public class AddPenaltyController extends PenaltyPopupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCategoriesList();
    }

    public void handleSavePenaltyAction() {

        if (validateInput()) {

            String title =  fx_title.getText();
            String description = fx_description.getText();
            Category category = fx_category.getValue();

            Penalty penalty = new Penalty(title, description, category);

            TransferObject response = FrontController.handleRequest(ADD, PENALTY, penalty);

            if(response.getOperationResult()) {
                closeWindow();
                AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Multa \u00E8 aggiunta correttamente.");
                // Update Users view
                FrontController.handleRequest("Penalties");
            }

        }
    }
}
