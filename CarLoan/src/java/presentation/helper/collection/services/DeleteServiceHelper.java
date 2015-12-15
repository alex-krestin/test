package presentation.helper.collection.services;

import business.appservice.ServiceManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.Service;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static presentation.helper.EntityCode.SERVICE;
import static presentation.helper.OperationCode.CHANGE_STATUS;


public class DeleteServiceHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeleteServiceHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        Service service = (Service) to;

        try {
            ServiceManager manager = new ServiceManager();
            response = manager.deleteService(service);
        } catch (MySQLIntegrityConstraintViolationException e) {
            Boolean result = AlertHandler.showAlertConfirmationDialog("Errore di cancellazione",
                    "Impossibile cancellare servizio. Vuoi procedere con l'archivazione", "Ci sono i dati collegati " +
                            "al servizio. Tuttavia è possibile disabilitare il servizio procedendo con l'archivazione. " +
                            "In questo caso il servizio verra disabilitato ma i dati non verranno cancellati.",
                    "Archivia");
            if(result) {
                response = FrontController.handleRequest(CHANGE_STATUS, SERVICE, service);

                if (response.getOperationResult()) {
                    AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Servizio è succesivamente archiviato.");
                }

                return response;
            }
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        if (response.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Servizio è succesivamente cancellato.");
        }

        return response;
    }
}
