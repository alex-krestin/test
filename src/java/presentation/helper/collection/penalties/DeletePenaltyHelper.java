package presentation.helper.collection.penalties;

import business.PenaltyManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Penalty;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.helper.EntityCode;
import presentation.helper.Helper;

import static presentation.helper.OperationCode.CHANGE_STATUS;


public class DeletePenaltyHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // Create Default Transfer Object
        TransferObject responce = new TransferObject();
        // cast TO to needed object
        Penalty penalty = (Penalty) to;

        try {
            PenaltyManager manager = new PenaltyManager();
            responce = manager.deletePenalty(penalty);
        } catch (MySQLIntegrityConstraintViolationException e) {
            //TODO Change text
            Boolean result = AlertHandler.showAlertConfirmationDialog("Errore di cancellazione",
                    "Impossibile cancellare penale. Vuoi procedere con l'archivazione", "Ci sono i dati collegati " +
                            "al penale. Tuttavia è possibile disabilitare l'accessorio procedendo con l'archivazione. " +
                            "In questo caso l'accessorio verra disabilitato ma i dati non verranno cancellati.",
                    "Archivia");
            if(result) {
                responce = FrontController.handleRequest(CHANGE_STATUS, EntityCode.PENALTY, penalty);

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
