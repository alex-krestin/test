package presentation.controller.penalties.tariffs;


import entity.PenaltyTariff;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.PENALTY_TARIFF;

public class AddPenaltyTariffController extends PenaltyTariffPopupController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPenaltyComboBox(fx_penalty);
        if (currentTariff != null) {
            fx_penalty.setValue(currentTariff.getPenalty());
        }
        addFieldsRestriction();
    }

    public void handleSaveTariffAction() {
        if (validateInput()) {
            PenaltyTariff tariff = getInputData();
            executeInsert(PENALTY_TARIFF, tariff);
        }
    }

}
