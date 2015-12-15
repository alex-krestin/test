package presentation.helper.collection.clients;

import business.appservice.ClientManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Client;
import entity.Response;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class DeleteClientHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeleteClientHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        Client client = (Client) to;

        try {
            ClientManager manager = new ClientManager();
            response = manager.deleteClient(client);
        } catch (MySQLIntegrityConstraintViolationException e) {
            AlertHandler.showAlert(Alert.AlertType.ERROR, "Errore", "Impossibile cancellare cliente. " +
                    "Ci sono i contratti collegati.");
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        if (response.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Cliente è succesivamente cancellato.");
        }

        return response;
    }
}
