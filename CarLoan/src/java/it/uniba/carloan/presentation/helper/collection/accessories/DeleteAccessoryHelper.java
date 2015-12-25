package it.uniba.carloan.presentation.helper.collection.accessories;

import it.uniba.carloan.business.appservice.generics.AccessoryManager;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Accessory;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.FrontController;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;
import static it.uniba.carloan.presentation.AlertHandler.showAlert;
import static it.uniba.carloan.presentation.helper.EntityCode.ACCESSORY;
import static it.uniba.carloan.presentation.helper.OperationCode.CHANGE_STATUS;
import static javafx.scene.control.Alert.AlertType.INFORMATION;


public class DeleteAccessoryHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeleteAccessoryHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        Accessory accessory = (Accessory) to;
        Response response = new Response();

        try {
            AccessoryManager manager = getApplicationService().getAccessoryManager();
            response = manager.deleteAccessory(accessory);
            showAlert(INFORMATION, "Successo", "Accessorio è succesivamente cancellato."); //TODO Change text
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        } catch (IntegrityConstraintViolationException e) {
            //TODO Change text
            Boolean result = AlertHandler.showAlertConfirmationDialog("Errore di cancellazione",
                    "Impossibile cancellare accessorio. Vuoi procedere con l'archivazione", "Ci sono i dati collegati " +
                            "all'accessorio. Tuttavia è possibile disabilitare l'accessorio procedendo con l'archivazione. " +
                            "In questo caso l'accessorio verra disabilitato ma i dati non verranno cancellati.",
                    "Archivia");
            if(result) {
                response = FrontController.handleRequest(CHANGE_STATUS, ACCESSORY, accessory);
                showAlert(INFORMATION, "Successo", "Accessorio è succesivamente archiviato.");
            }
        }

        return response;
    }
}
