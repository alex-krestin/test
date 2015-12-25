package it.uniba.carloan.presentation.helper.collection.accessories;


import it.uniba.carloan.business.appservice.generics.AccessoryManager;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.AccessoryTariff;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;
import static it.uniba.carloan.presentation.AlertHandler.showAlert;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class DeleteAccessoryTariffHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeleteAccessoryTariffHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        // cast TO to needed object
        AccessoryTariff tariff = (AccessoryTariff) to;
        Response response = new Response();

        try {
            AccessoryManager manager = getApplicationService().getAccessoryManager();
            response = manager.deleteTariff(tariff);
            showAlert(INFORMATION, "Successo", "Tariffo è succesivamente eliminato."); //TODO Change text
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        } catch (IntegrityConstraintViolationException e) {
            // will never be thrown
        }

        return response;
    }
}
