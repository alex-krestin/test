package presentation.helper.collection.penalties;

import business.appservice.PenaltyManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.TransferObject;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class GetAllPenaltiesHelper implements Helper {
    private static final Logger log = Logger.getLogger(GetAllPenaltiesHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // to is null
        Response response = new Response();

        try {
            PenaltyManager manager = new PenaltyManager();
            response = manager.getAllPenalties();
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
