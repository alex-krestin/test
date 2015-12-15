package presentation.helper.collection.penalties;


import business.appservice.PenaltyManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.PenaltyTariff;
import entity.Response;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.helper.Helper;
import presentation.helper.exception.InvalidTariffDateException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdatePenaltyTariffHelper implements Helper {
    private static final Logger log = Logger.getLogger(UpdatePenaltyTariffHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        PenaltyTariff tariff = (PenaltyTariff) to;

        try {
            PenaltyManager manager = new PenaltyManager();
            response = manager.updateTariff(tariff);
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        } catch (InvalidTariffDateException e) {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione!", "Le date scelte si sovrappongono " +
                    "con la tariffa esistente");
        }

        return response;
    }
}
