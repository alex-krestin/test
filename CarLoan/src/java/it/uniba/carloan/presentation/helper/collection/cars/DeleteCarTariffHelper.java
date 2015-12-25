package it.uniba.carloan.presentation.helper.collection.cars;


import it.uniba.carloan.business.appservice.ServiceFactory;
import it.uniba.carloan.business.appservice.generics.CarManager;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.CarTariff;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteCarTariffHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeleteCarTariffHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        CarTariff tariff = (CarTariff) to;

        try {
            CarManager manager = ServiceFactory.getApplicationService().getCarManager();
            response = manager.deleteTariff(tariff);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        } catch (IntegrityConstraintViolationException e) {
            // will never be thrown
        }

        /*
        if (response.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Tariffo è succesivamente eliminato."); //TODO Change text
        }*/

        return response;
    }
}
