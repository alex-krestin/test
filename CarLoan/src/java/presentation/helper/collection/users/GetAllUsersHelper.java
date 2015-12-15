package presentation.helper.collection.users;

import business.appservice.UserManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.TransferObject;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GetAllUsersHelper implements Helper {
    private static final Logger log = Logger.getLogger(GetAllUsersHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // to is null
        Response response = new Response();

        try {
            UserManager userManager = new UserManager();
            response = userManager.getAllUsers();
        } catch (UnknownDatabaseTypeException | InstantiationException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }
        return response;
    }
}
