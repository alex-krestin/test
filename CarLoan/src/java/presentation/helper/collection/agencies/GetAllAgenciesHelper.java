package presentation.helper.collection.agencies;

import business.appservice.AgencyManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.TransferObject;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GetAllAgenciesHelper implements Helper {
    private static final Logger log = Logger.getLogger(GetAllAgenciesHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // to is null
        Response response = new Response();

        try {
            AgencyManager manager = new AgencyManager();
            response = manager.getAllAgencies();
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }
        
        return response;
    }
}
