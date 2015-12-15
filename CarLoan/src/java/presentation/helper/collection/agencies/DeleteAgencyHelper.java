package presentation.helper.collection.agencies;

import business.appservice.AgencyManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Agency;
import entity.Response;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static presentation.helper.EntityCode.AGENCY;
import static presentation.helper.OperationCode.CHANGE_STATUS;


public class DeleteAgencyHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeleteAgencyHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        Agency agency = (Agency) to;


        try {
            AgencyManager agencyManager = new AgencyManager();
            response = agencyManager.deleteAgency(agency);
        } catch (MySQLIntegrityConstraintViolationException e) {
            Boolean result = AlertHandler.showAlertConfirmationDialog("Errore di cancellazione",
                    "Impossibile cancellare agenzia. Vuoi procedere con l'archivazione", "Ci sono i dati collegati " +
                            "all'agenzia. Tuttavia è possibile chiudere l'agenzia procedendo con l'archivazione. " +
                            "In questo caso l'agenzia verra disattivata ma i dati non verranno cancellati.",
                    "Archivia");
            if(result) {
                response = FrontController.handleRequest(CHANGE_STATUS, AGENCY, agency);

                if (response.getOperationResult()) {
                    AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Agenzia è succesivamente archiviata.");
                }

                return response;
            }
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        if (response.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Agenzia è succesivamente cancellata.");
        }

        return response;
    }
}
