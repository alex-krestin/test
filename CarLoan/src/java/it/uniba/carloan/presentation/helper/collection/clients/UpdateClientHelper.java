package it.uniba.carloan.presentation.helper.collection.clients;

import it.uniba.carloan.business.appservice.generics.ClientManager;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Client;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;


public class UpdateClientHelper implements Helper {
    private static final Logger log = Logger.getLogger(UpdateClientHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        Client client = (Client) to;

        try {
            ClientManager manager = getApplicationService().getClientManager();
            response = manager.updateClient(client);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        } catch (DuplicateEntryException e) {
            e.showAlert();
        }

        return response;
    }
}
