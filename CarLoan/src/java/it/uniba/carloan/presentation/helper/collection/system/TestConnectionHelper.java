package it.uniba.carloan.presentation.helper.collection.system;

import it.uniba.carloan.business.appservice.generics.SystemManager;
import it.uniba.carloan.business.security.DatabaseConfigObject;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;


public class TestConnectionHelper implements Helper {
    private static final Logger log = Logger.getLogger(TestConnectionHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        DatabaseConfigObject object = (DatabaseConfigObject) to;
        Response response = new Response();

        try {
            SystemManager connectionManager = getApplicationService().getSystemManager();
            response = connectionManager.checkConnection(object);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        }

        return response;
    }
}
