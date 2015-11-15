package presentation.helper.collection.services;

import business.ServiceManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Service;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.helper.Helper;

import static presentation.helper.EntityCode.SERVICE;
import static presentation.helper.OperationCode.CHANGE_STATUS;


public class DeleteServiceHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // Create Default Transfer Object
        TransferObject responce = new TransferObject();
        // cast TO to needed object
        Service service = (Service) to;

        try {
            ServiceManager manager = new ServiceManager();
            responce = manager.deleteService(service);
        } catch (MySQLIntegrityConstraintViolationException e) {
            Boolean result = AlertHandler.showAlertConfirmationDialog("Errore di cancellazione",
                    "Impossibile cancellare servizio. Vuoi procedere con l'archivazione", "Ci sono i dati collegati " +
                            "al servizio. Tuttavia è possibile disabilitare il servizio procedendo con l'archivazione. " +
                            "In questo caso il servizio verra disabilitato ma i dati non verranno cancellati.",
                    "Archivia");
            if(result) {
                responce = FrontController.handleRequest(CHANGE_STATUS, SERVICE, service);

                if (responce.getOperationResult()) {
                    AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Servizio è succesivamente archiviato.");
                }

                return responce;
            }
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (responce.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Servizio è succesivamente cancellato.");
        }

        return responce;
    }
}
