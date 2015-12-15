package presentation.controller.penalties.tariffs;


import entity.PenaltyTariff;
import presentation.helper.EntityCode;

import java.net.URL;
import java.util.ResourceBundle;

public class EditPenaltyTariffController extends PenaltyTariffPopupController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPenaltyComboBox(fx_penalty);
        fx_penalty.setValue(currentTariff.getPenalty());
        fx_price.setText(currentTariff.getPrice().toString());
        fx_fromDate.setValue(currentTariff.getFromDate());
        if (currentTariff.getToDate() != null) {
            fx_toDate.setValue(currentTariff.getToDate());
        }
        addFieldsRestriction();
    }


    public void handleEditTariffAction() {
        if (validateInput()) {
            PenaltyTariff tariff = getInputData();
            executeUpdate(currentTariff, tariff, EntityCode.PENALTY_TARIFF);
        }
    }
}
