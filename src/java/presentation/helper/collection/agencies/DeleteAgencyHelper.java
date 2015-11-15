package presentation.helper.collection.agencies;

import business.AgencyManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Agency;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.helper.Helper;

import static presentation.helper.EntityCode.AGENCY;
import static presentation.helper.OperationCode.CHANGE_STATUS;


public class DeleteAgencyHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // Create Default Transfer Object
        TransferObject responce = new TransferObject();
        // cast TO to needed object
        Agency agency = (Agency) to;


        try {
            AgencyManager agencyManager = new AgencyManager();
            responce = agencyManager.deleteAgency(agency);
        } catch (MySQLIntegrityConstraintViolationException e) {
            Boolean result = AlertHandler.showAlertConfirmationDialog("Errore di cancellazione",
                    "Impossibile cancellare agenzia. Vuoi procedere con l'archivazione", "Ci sono i dati collegati " +
                            "all'agenzia. Tuttavia è possibile chiudere l'agenzia procedendo con l'archivazione. " +
                            "In questo caso l'agenzia verra disattivata ma i dati non verranno cancellati.",
                    "Archivia");
            if(result) {
                responce = FrontController.handleRequest(CHANGE_STATUS, AGENCY, agency);

                if (responce.getOperationResult()) {
                    AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Agenzia è succesivamente archiviata.");
                }

                return responce;
            }
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (responce.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Agenzia è succesivamente cancellata.");
        }

        return responce;
    }
}
