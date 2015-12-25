package it.uniba.carloan.presentation.helper.collection.clients;

import it.uniba.carloan.business.appservice.generics.ClientManager;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Client;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;


public class DeleteClientHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeleteClientHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        Client client = (Client) to;

        try {
            ClientManager manager = getApplicationService().getClientManager();
            response = manager.deleteClient(client);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        } catch (IntegrityConstraintViolationException e) {
            e.printStackTrace();
        }

        /*

        catch (IntegrityConstraintViolationException e) {
            AlertHandler.showAlert(Alert.AlertType.ERROR, "Errore", "Impossibile cancellare cliente. " +
                    "Ci sono i contratti collegati.");
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        if (response.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Cliente è succesivamente cancellato.");
        }*/

        return response;
    }
}
