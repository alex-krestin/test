package presentation.controller.penalties;

import entity.Penalty;
import entity.Response;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.PENALTY;
import static presentation.helper.EntityCode.PENALTY_CATEGORY;
import static presentation.helper.OperationCode.ADD;


public class AddPenaltyController extends PenaltyPopupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_category.loadValues(PENALTY_CATEGORY);
    }

    @SuppressWarnings("LawOfDemeter")
    public void handleSavePenaltyAction() {

        if (validateInput()) {
            Penalty penalty = new Penalty.Builder()
                    .title(fx_title.getText())
                    .description(fx_description.getText())
                    .category(fx_category.getValue()).build();

            Response response = FrontController.handleRequest(ADD, PENALTY, penalty);

            if(response.getOperationResult()) {
                closeWindow();
                AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Multa \u00E8 aggiunta correttamente.");
                // Update Users view
                FrontController.handleRequest("Penalties");
            }

        }
    }
}
