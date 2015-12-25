package it.uniba.carloan.presentation.helper.collection.services;

import it.uniba.carloan.business.appservice.generics.ServiceManager;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.Service;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;

public class UpdateServiceHelper implements Helper {
    private static final Logger log = Logger.getLogger(UpdateServiceHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        Service service = (Service) to;

        try {
            ServiceManager manager = getApplicationService().getServiceManager();
            response = manager.updateService(service);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        } catch (DuplicateEntryException e) {
            e.showAlert();
        }

        return response;
    }
}
