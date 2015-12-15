package presentation.helper.collection.accessories;


import business.appservice.AccessoryManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.UnknownDatabaseTypeException;
import entity.AccessoryTariff;
import entity.Response;
import entity.Tariff;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteAccessoryTariffHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeleteAccessoryTariffHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        Tariff tariff = (AccessoryTariff) to;

        try {
            AccessoryManager manager = new AccessoryManager();
            response = manager.deleteTariff(tariff);
        } catch (MySQLIntegrityConstraintViolationException e) {
            //TODO Change text
            AlertHandler.showAlert(Alert.AlertType.ERROR, "Errore di cancellazione",
                    "Impossibile eliminare il tariffo in quanto è in uso.");
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        if (response.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Tariffo è succesivamente eliminato."); //TODO Change text
        }

        return response;
    }
}
