package presentation.helper.collection.services;


import business.appservice.ServiceManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.ServiceTariff;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.helper.Helper;
import presentation.helper.exception.InvalidTariffDateException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateServiceTariffHelper implements Helper {
    private static final Logger log = Logger.getLogger(UpdateServiceTariffHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        ServiceTariff tariff = (ServiceTariff) to;

        try {
            ServiceManager manager = new ServiceManager();
            response = manager.updateTariff(tariff);
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        } catch (InvalidTariffDateException e) {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione!", "Le date scelte si sovrappongono " +
                    "con la tariffa esistente");
        }

        return response;
    }
}
