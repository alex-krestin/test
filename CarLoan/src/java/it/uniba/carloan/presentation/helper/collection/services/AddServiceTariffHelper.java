package it.uniba.carloan.presentation.helper.collection.services;


import it.uniba.carloan.business.appservice.ServiceFactory;
import it.uniba.carloan.business.appservice.generics.ServiceManager;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.ServiceTariff;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;
import it.uniba.carloan.presentation.helper.exception.InvalidTariffDateException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddServiceTariffHelper implements Helper {
    private static final Logger log = Logger.getLogger(AddServiceTariffHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        ServiceTariff tariff = (ServiceTariff) to;

        try {
            ServiceManager manager = ServiceFactory.getApplicationService().getServiceManager();
            response = manager.addTariff(tariff);
        } catch (DuplicateEntryException | InvalidTariffDateException e) {
            e.showAlert();
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        }

        return response;
    }
}
