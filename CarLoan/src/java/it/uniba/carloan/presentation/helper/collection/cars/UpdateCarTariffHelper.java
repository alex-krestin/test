package it.uniba.carloan.presentation.helper.collection.cars;


import it.uniba.carloan.business.appservice.generics.CarManager;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.CarTariff;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;
import it.uniba.carloan.presentation.helper.exception.InvalidTariffDateException;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;

public class UpdateCarTariffHelper implements Helper {
    private static final Logger log = Logger.getLogger(UpdateCarTariffHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        CarTariff tariff = (CarTariff) to;

        try {
            CarManager manager = getApplicationService().getCarManager();
            response = manager.updateTariff(tariff);
        } catch (DuplicateEntryException | InvalidTariffDateException e) {
            e.showAlert();
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        }

        return response;
    }
}
