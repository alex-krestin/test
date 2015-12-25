package it.uniba.carloan.presentation.helper.collection.agencies;

import it.uniba.carloan.business.appservice.generics.AgencyManager;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Agency;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.FrontController;
import it.uniba.carloan.presentation.helper.Helper;
import javafx.scene.control.Alert;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;
import static it.uniba.carloan.presentation.helper.EntityCode.AGENCY;
import static it.uniba.carloan.presentation.helper.OperationCode.CHANGE_STATUS;


public class DeleteAgencyHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeleteAgencyHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        Agency agency = (Agency) to;


        try {
            AgencyManager manager = getApplicationService().getAgencyManager();
            response = manager.deleteAgency(agency);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        } catch (IntegrityConstraintViolationException e) {
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
        }
/*
        if (response.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Agenzia è succesivamente cancellata.");
        }
*/
        return response;
    }
}
