package presentation.helper.collection.accessories;

import business.appservice.AccessoryManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Accessory;
import entity.Response;
import entity.TransferObject;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class ChangeAccessoryStatusHelper implements Helper {
    private static final Logger log = Logger.getLogger(ChangeAccessoryStatusHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        Accessory accessory = (Accessory) to;

        try {
            AccessoryManager manager = new AccessoryManager();
            response = manager.changeAccessoryState(accessory);
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
