package it.uniba.carloan.presentation.helper.collection.accessories;

import it.uniba.carloan.business.appservice.generics.AccessoryManager;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Accessory;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;


public class UpdateAccessoryHelper implements Helper {
    private static final Logger log = Logger.getLogger(UpdateAccessoryHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        Accessory accessory = (Accessory) to;
        Response response = new Response();

        try {
            AccessoryManager manager = getApplicationService().getAccessoryManager();
            response = manager.updateAccessory(accessory);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        } catch (DuplicateEntryException e) {
            e.showAlert();
        }

        return response;
    }
}
