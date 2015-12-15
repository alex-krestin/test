package presentation.helper.collection.penalties;

import business.appservice.PenaltyManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Penalty;
import entity.Response;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.helper.EntityCode;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static presentation.helper.OperationCode.CHANGE_STATUS;


public class DeletePenaltyHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeletePenaltyHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        Penalty penalty = (Penalty) to;

        try {
            PenaltyManager manager = new PenaltyManager();
            response = manager.deletePenalty(penalty);
        } catch (MySQLIntegrityConstraintViolationException e) {
            //TODO Change text
            Boolean result = AlertHandler.showAlertConfirmationDialog("Errore di cancellazione",
                    "Impossibile cancellare penale. Vuoi procedere con l'archivazione", "Ci sono i dati collegati " +
                            "al penale. Tuttavia è possibile disabilitare l'accessorio procedendo con l'archivazione. " +
                            "In questo caso l'accessorio verra disabilitato ma i dati non verranno cancellati.",
                    "Archivia");
            if(result) {
                response = FrontController.handleRequest(CHANGE_STATUS, EntityCode.PENALTY, penalty);

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
