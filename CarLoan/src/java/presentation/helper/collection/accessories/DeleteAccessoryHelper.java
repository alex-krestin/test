package presentation.helper.collection.accessories;

import business.appservice.AccessoryManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Accessory;
import entity.Response;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static presentation.helper.EntityCode.ACCESSORY;
import static presentation.helper.OperationCode.CHANGE_STATUS;


public class DeleteAccessoryHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeleteAccessoryHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        Accessory accessory = (Accessory) to;

        try {
            AccessoryManager manager = new AccessoryManager();
            response = manager.deleteAccessory(accessory);
        } catch (MySQLIntegrityConstraintViolationException e) {
            //TODO Change text
            Boolean result = AlertHandler.showAlertConfirmationDialog("Errore di cancellazione",
                    "Impossibile cancellare accessorio. Vuoi procedere con l'archivazione", "Ci sono i dati collegati " +
                            "all'accessorio. Tuttavia è possibile disabilitare l'accessorio procedendo con l'archivazione. " +
                            "In questo caso l'accessorio verra disabilitato ma i dati non verranno cancellati.",
                    "Archivia");
            if(result) {
                response = FrontController.handleRequest(CHANGE_STATUS, ACCESSORY, accessory);

                if (response.getOperationResult()) {
                    AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Accessorio è succesivamente archiviato.");
                }

                return response;
            }
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        if (response.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Accessorio è succesivamente cancellato."); //TODO Change text
        }

        return response;
    }
}
