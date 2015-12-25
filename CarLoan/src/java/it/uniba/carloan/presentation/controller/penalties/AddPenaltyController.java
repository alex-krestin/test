package it.uniba.carloan.presentation.controller.penalties;

import it.uniba.carloan.entity.Penalty;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.FrontController;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.PENALTY;
import static it.uniba.carloan.presentation.helper.EntityCode.PENALTY_CATEGORY;
import static it.uniba.carloan.presentation.helper.OperationCode.ADD;


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
