package presentation.helper.collection.agencies;

import business.appservice.AgencyManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Agency;
import entity.Response;
import entity.TransferObject;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class ChangeAgencyStateHelper implements Helper {
    private static final Logger log = Logger.getLogger(ChangeAgencyStateHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        Agency agency = (Agency) to;

        try {
            AgencyManager manager = new AgencyManager();
            response = manager.changeAgencyState(agency);
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
