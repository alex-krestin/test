package it.uniba.carloan.presentation.helper.collection.services;


import it.uniba.carloan.business.appservice.ServiceFactory;
import it.uniba.carloan.business.appservice.generics.ServiceManager;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.ServiceTariff;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteServiceTariffHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeleteServiceTariffHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        ServiceTariff tariff = (ServiceTariff) to;

        try {
            ServiceManager manager = ServiceFactory.getApplicationService().getServiceManager();
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
        }
*/
        return response;
    }
}
