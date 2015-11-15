package presentation.helper.collection.accessories;

import business.AccessoryManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Accessory;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.helper.Helper;

import static presentation.helper.EntityCode.*;
import static presentation.helper.OperationCode.CHANGE_STATUS;


public class DeleteAccessoryHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // Create Default Transfer Object
        TransferObject responce = new TransferObject();
        // cast TO to needed object
        Accessory accessory = (Accessory) to;

        try {
            AccessoryManager manager = new AccessoryManager();
            responce = manager.deleteAccessory(accessory);
        } catch (MySQLIntegrityConstraintViolationException e) {
            //TODO Change text
            Boolean result = AlertHandler.showAlertConfirmationDialog("Errore di cancellazione",
                    "Impossibile cancellare accessorio. Vuoi procedere con l'archivazione", "Ci sono i dati collegati " +
                            "all'accessorio. Tuttavia è possibile disabilitare l'accessorio procedendo con l'archivazione. " +
                            "In questo caso l'accessorio verra disabilitato ma i dati non verranno cancellati.",
                    "Archivia");
            if(result) {
                responce = FrontController.handleRequest(CHANGE_STATUS, ACCESSORY, accessory);

                if (responce.getOperationResult()) {
                    AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Accessorio è succesivamente archiviato.");
                }

                return responce;
            }
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            e.printStackTrace(); //TODO exception Handler
        }

        if (responce.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Accessorio è succesivamente cancellato."); //TODO Change text
        }

        return responce;
    }
}
