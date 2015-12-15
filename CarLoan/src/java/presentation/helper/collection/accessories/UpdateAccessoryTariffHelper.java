package presentation.helper.collection.accessories;


import business.appservice.AccessoryManager;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.AccessoryTariff;
import entity.Response;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.helper.Helper;
import presentation.helper.exception.InvalidTariffDateException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateAccessoryTariffHelper implements Helper {
    private static final Logger log = Logger.getLogger(UpdateAccessoryTariffHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        AccessoryTariff tariff = (AccessoryTariff) to;

        try {
            AccessoryManager manager = new AccessoryManager();
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
