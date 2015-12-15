package presentation.helper.collection.clients;

import business.appservice.ClientManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.TransferObject;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class GetAllClientsHelper implements Helper {
    private static final Logger log = Logger.getLogger(GetAllClientsHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // to is null
        Response response = new Response();

        try {
            ClientManager manager = new ClientManager();
            response = manager.getAllClients();
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
