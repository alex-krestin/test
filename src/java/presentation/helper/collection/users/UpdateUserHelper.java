package presentation.helper.collection.users;

import business.UserManager;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.TransferObject;
import entity.User;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.helper.Helper;


public class UpdateUserHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // Create Default Transfer Object
        TransferObject responce = new TransferObject();
        // cast TO to needed object
        User user = (User) to;

        try {
            UserManager userManager = new UserManager();
            responce = userManager.updateUser(user);
        } catch (UnknownDatabaseTypeException e) {
            // never be reached
        } catch (InstantiationException | IllegalAccessException e) {
            AlertHandler.showException("Errore di connessione al database.", e);
        } catch (DuplicateEntryException e) {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione!", e.getCustomMessage());
        }

        return responce;
    }
}
