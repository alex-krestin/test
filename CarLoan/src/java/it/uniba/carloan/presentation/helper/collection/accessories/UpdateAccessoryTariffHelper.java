package it.uniba.carloan.presentation.helper.collection.accessories;


import it.uniba.carloan.business.appservice.ServiceFactory;
import it.uniba.carloan.business.appservice.generics.AccessoryManager;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.AccessoryTariff;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;
import it.uniba.carloan.presentation.helper.exception.InvalidTariffDateException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateAccessoryTariffHelper implements Helper {
    private static final Logger log = Logger.getLogger(UpdateAccessoryTariffHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        AccessoryTariff tariff = (AccessoryTariff) to;
        Response response = new Response();

        try {
            AccessoryManager manager = ServiceFactory.getApplicationService().getAccessoryManager();
            response = manager.updateTariff(tariff);
        } catch (DuplicateEntryException | InvalidTariffDateException e) {
            e.showAlert();
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        }

        return response;
    }
}
