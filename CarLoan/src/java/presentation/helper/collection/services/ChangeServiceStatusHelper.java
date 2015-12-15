package presentation.helper.collection.services;

import business.appservice.ServiceManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.Service;
import entity.TransferObject;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class ChangeServiceStatusHelper implements Helper {
    private static final Logger log = Logger.getLogger(ChangeServiceStatusHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        Service service = (Service) to;

        try {
            ServiceManager manager = new ServiceManager();
            response = manager.changeServiceStatus(service);
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
