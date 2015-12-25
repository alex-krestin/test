package it.uniba.carloan.presentation.helper.collection.agencies;

import it.uniba.carloan.business.appservice.generics.AgencyManager;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Agency;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;


public class ChangeAgencyStateHelper implements Helper {
    private static final Logger log = Logger.getLogger(ChangeAgencyStateHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        Agency agency = (Agency) to;

        try {
            AgencyManager manager = getApplicationService().getAgencyManager();
            response = manager.changeAgencyState(agency);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        }

        return response;
    }
}
