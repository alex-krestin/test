package it.uniba.carloan.presentation.helper.collection.penalties;


import it.uniba.carloan.business.appservice.generics.PenaltyManager;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.PenaltyTariff;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.helper.Helper;
import javafx.scene.control.Alert;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;

public class DeletePenaltyTariffHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeletePenaltyTariffHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        PenaltyTariff tariff = (PenaltyTariff) to;

        try {
            PenaltyManager manager = getApplicationService().getPenaltyManager();
            response = manager.deleteTariff(tariff);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        } catch (IntegrityConstraintViolationException e) {
            // will never be throw
        }


        if (response.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Tariffo è succesivamente eliminato."); //TODO Change text
        }

        return response;
    }
}
