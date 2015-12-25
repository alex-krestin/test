package it.uniba.carloan.presentation.controller.penalties;

import it.uniba.carloan.entity.Penalty;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.PENALTY;
import static it.uniba.carloan.presentation.helper.EntityCode.PENALTY_CATEGORY;


public class EditPenaltyController extends PenaltyPopupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_category.loadValues(PENALTY_CATEGORY);

        fx_title.setText(currentPenalty.getTitle());
        fx_description.setText(currentPenalty.getDescription());
        fx_category.getSelectionModel().select(currentPenalty.getCategory());
    }

    @SuppressWarnings("LawOfDemeter")
    public void handleEditPenaltyAction() {
        if (validateInput()) {

            Penalty penalty = new Penalty.Builder()
                    .id(currentPenalty.getId())
                    .title(fx_title.getText())
                    .description(fx_description.getText())
                    .category(fx_category.getValue()).build();

            executeUpdate(currentPenalty, penalty, PENALTY);
        }
    }
}