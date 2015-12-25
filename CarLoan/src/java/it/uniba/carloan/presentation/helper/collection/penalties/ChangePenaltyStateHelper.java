package it.uniba.carloan.presentation.helper.collection.penalties;

import it.uniba.carloan.business.appservice.generics.PenaltyManager;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Penalty;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;


public class ChangePenaltyStateHelper implements Helper {
    private static final Logger log = Logger.getLogger(ChangePenaltyStateHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        Penalty penalty = (Penalty) to;

        try {
            PenaltyManager manager = getApplicationService().getPenaltyManager();
            response = manager.changePenaltyState(penalty);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        }

        return response;
    }
}
