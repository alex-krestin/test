package it.uniba.carloan.presentation.helper.collection.penalties;

import it.uniba.carloan.business.appservice.generics.PenaltyManager;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;


public class GetAllPenaltyCategoriesHelper implements Helper {
    private static final Logger log = Logger.getLogger(GetAllPenaltyCategoriesHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();

        try {
            PenaltyManager manager = getApplicationService().getPenaltyManager();
            response = manager.getAllCategories();
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        }

        return response;
    }
}
