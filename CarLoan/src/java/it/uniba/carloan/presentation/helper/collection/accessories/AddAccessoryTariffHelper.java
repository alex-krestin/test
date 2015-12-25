package it.uniba.carloan.presentation.helper.collection.accessories;


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

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;

public class AddAccessoryTariffHelper implements Helper {
    private static final Logger log = Logger.getLogger(AddAccessoryTariffHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        AccessoryTariff tariff = (AccessoryTariff) to;
        Response response = new Response();

        try {
            AccessoryManager manager = getApplicationService().getAccessoryManager();
            response = manager.addTariff(tariff);
        } catch (DuplicateEntryException e) {
            e.showAlert();
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        } catch (InvalidTariffDateException e) {
            e.showAlert();
        }

        return response;
    }
}
