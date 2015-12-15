package presentation.helper.collection.cars;


import business.appservice.CarManager;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.CarTariff;
import entity.Response;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.helper.Helper;
import presentation.helper.exception.InvalidTariffDateException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateCarTariffHelper implements Helper {
    private static final Logger log = Logger.getLogger(UpdateCarTariffHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        CarTariff tariff = (CarTariff) to;

        try {
            CarManager manager = new CarManager();
            response = manager.updateTariff(tariff);
        } catch (DuplicateEntryException e) {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", e.getCustomMessage());
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        } catch (InvalidTariffDateException e) {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione!", "Le date scelte si sovrappongono " +
                    "con la tariffa esistente");
        }

        return response;
    }
}
