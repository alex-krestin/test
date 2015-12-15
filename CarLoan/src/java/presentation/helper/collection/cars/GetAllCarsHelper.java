package presentation.helper.collection.cars;

import business.appservice.CarManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.TransferObject;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class GetAllCarsHelper implements Helper {
    private static final Logger log = Logger.getLogger(GetAllCarsHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // to is null
        Response response = new Response();

        try {
            CarManager manager = new CarManager();
            response = manager.getAllCars();
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
