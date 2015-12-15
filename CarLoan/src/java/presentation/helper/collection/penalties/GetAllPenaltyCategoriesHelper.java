package presentation.helper.collection.penalties;

import business.appservice.PenaltyManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.TransferObject;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class GetAllPenaltyCategoriesHelper implements Helper {
    private static final Logger log = Logger.getLogger(GetAllPenaltyCategoriesHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // to is null
        Response response = new Response();

        try {
            PenaltyManager manager = new PenaltyManager();
            response = manager.getAllCategories();
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
