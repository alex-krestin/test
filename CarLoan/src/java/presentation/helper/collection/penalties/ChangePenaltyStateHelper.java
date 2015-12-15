package presentation.helper.collection.penalties;

import business.appservice.PenaltyManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Penalty;
import entity.Response;
import entity.TransferObject;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class ChangePenaltyStateHelper implements Helper {
    private static final Logger log = Logger.getLogger(ChangePenaltyStateHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        Penalty penalty = (Penalty) to;

        try {
            PenaltyManager manager = new PenaltyManager();
            response = manager.changePenaltyState(penalty);
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
