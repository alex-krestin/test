package it.uniba.carloan.presentation.helper.collection.services;

import it.uniba.carloan.business.appservice.ServiceFactory;
import it.uniba.carloan.business.appservice.generics.ServiceManager;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.Service;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class DeleteServiceHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeleteServiceHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        Service service = (Service) to;

        try {
            ServiceManager manager = ServiceFactory.getApplicationService().getServiceManager();
            response = manager.deleteService(service);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        } catch (IntegrityConstraintViolationException e) {
            e.printStackTrace();
        }

        /*
        catch (IntegrityConstraintViolationException e) {
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
        } catch (PersistenceException e) {
            e.printStackTrace();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        if (response.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Servizio è succesivamente cancellato.");
        }*/

        return response;
    }
}
