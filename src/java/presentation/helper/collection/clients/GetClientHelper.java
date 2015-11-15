package presentation.helper.collection.clients;

import business.ClientManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Client;
import entity.TransferObject;
import presentation.helper.Helper;


public class GetClientHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // Create Default Transfer Object
        TransferObject responce = new TransferObject();
        // cast TO to needed object
        Client client = (Client) to;

        try {
            ClientManager manager = new ClientManager();
            responce = manager.searchClient(client);
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return responce;
    }
}
