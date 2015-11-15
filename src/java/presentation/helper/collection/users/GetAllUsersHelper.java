package presentation.helper.collection.users;

import business.UserManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.TransferObject;
import presentation.AlertHandler;
import presentation.helper.Helper;

public class GetAllUsersHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // to is null
        TransferObject responce = new TransferObject();

        try {
            UserManager userManager = new UserManager();
            responce = userManager.getAllUsers();
        } catch (UnknownDatabaseTypeException e) {
            // never be reached
        } catch (InstantiationException | IllegalAccessException e) {
            AlertHandler.showException("Errore di connessione al database.", e);
        }
        return responce;
    }
}
