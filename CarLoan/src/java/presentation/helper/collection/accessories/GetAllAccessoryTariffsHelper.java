package presentation.helper.collection.accessories;


import business.appservice.AccessoryManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.TransferObject;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GetAllAccessoryTariffsHelper implements Helper {
    private static final Logger log = Logger.getLogger(GetAllAccessoryTariffsHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // to is null
        Response response = new Response();

        try {
            AccessoryManager manager = new AccessoryManager();
            response = manager.getAllTariffs();
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
