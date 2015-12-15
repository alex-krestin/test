package presentation.helper.collection.services;


import business.appservice.ServiceManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.TransferObject;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GetAllServiceTariffsHelper implements Helper {
    private static final Logger log = Logger.getLogger(GetAllServiceTariffsHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // to is null
        Response response = new Response();

        try {
            ServiceManager manager = new ServiceManager();
            response = manager.getAllTariffs();
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
