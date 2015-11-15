package presentation.helper.collection.clients;

import business.ClientManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Client;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.helper.Helper;


public class DeleteClientHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // Create Default Transfer Object
        TransferObject responce = new TransferObject();
        // cast TO to needed object
        Client client = (Client) to;

        try {
            ClientManager manager = new ClientManager();
            responce = manager.deleteClient(client);
        } catch (MySQLIntegrityConstraintViolationException e) {
            AlertHandler.showAlert(Alert.AlertType.ERROR, "Errore", "Impossibile cancellare cliente. " +
                    "Ci sono i contratti collegati.");
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (responce.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Cliente è succesivamente cancellato.");
        }

        return responce;
    }
}
