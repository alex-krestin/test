package presentation.helper.collection.users;

import business.appservice.UserManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.TransferObject;
import entity.User;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangeAccessHelper implements Helper {
    private static final Logger log = Logger.getLogger(ChangeAccessHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        User user = (User) to;

        try {
            UserManager userManager = new UserManager();
            response = userManager.changeUserAccess(user);
        } catch (UnknownDatabaseTypeException | InstantiationException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
