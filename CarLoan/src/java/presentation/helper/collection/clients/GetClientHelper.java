package presentation.helper.collection.clients;

import business.appservice.ClientManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Client;
import entity.Response;
import entity.TransferObject;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class GetClientHelper implements Helper {
    private static final Logger log = Logger.getLogger(GetClientHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        Client client = (Client) to;

        try {
            ClientManager manager = new ClientManager();
            response = manager.searchClient(client);
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
