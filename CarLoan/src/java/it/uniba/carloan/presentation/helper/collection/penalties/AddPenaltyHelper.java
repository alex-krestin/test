package it.uniba.carloan.presentation.helper.collection.penalties;

import it.uniba.carloan.business.appservice.ServiceFactory;
import it.uniba.carloan.business.appservice.generics.PenaltyManager;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Penalty;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class AddPenaltyHelper implements Helper {
    private static final Logger log = Logger.getLogger(AddPenaltyHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        Penalty penalty = (Penalty) to;

        try {
            PenaltyManager manager = ServiceFactory.getApplicationService().getPenaltyManager();
            response = manager.addPenalty(penalty);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        } catch (DuplicateEntryException e) {
            e.showAlert();
        }

        return response;
    }
}
