package it.uniba.carloan.presentation.helper.collection.penalties;


import it.uniba.carloan.business.appservice.ServiceFactory;
import it.uniba.carloan.business.appservice.generics.PenaltyManager;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GetAllPenaltyTariffsHelper implements Helper {
    private static final Logger log = Logger.getLogger(GetAllPenaltyTariffsHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();

        try {
            PenaltyManager manager = ServiceFactory.getApplicationService().getPenaltyManager();
            response = manager.getAllTariffs();
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        }

        return response;
    }
}
