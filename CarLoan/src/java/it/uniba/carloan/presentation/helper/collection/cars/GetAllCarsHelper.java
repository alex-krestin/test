package it.uniba.carloan.presentation.helper.collection.cars;

import it.uniba.carloan.business.appservice.generics.CarManager;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;


public class GetAllCarsHelper implements Helper {
    private static final Logger log = Logger.getLogger(GetAllCarsHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();

        try {
            CarManager manager = getApplicationService().getCarManager();
            response = manager.getAllCars();
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        }

        return response;
    }
}
