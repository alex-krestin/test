package presentation.helper.collection.penalties;


import business.appservice.PenaltyManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.TransferObject;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GetAllPenaltyTariffsHelper implements Helper {
    private static final Logger log = Logger.getLogger(GetAllPenaltyTariffsHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // to is null
        Response response = new Response();

        try {
            PenaltyManager manager = new PenaltyManager();
            response = manager.getAllTariffs();
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
