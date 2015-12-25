package it.uniba.carloan.presentation.helper.collection.users;

import it.uniba.carloan.business.appservice.generics.UserManager;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.entity.User;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;

public class AddUserHelper implements Helper {
    private static final Logger log = Logger.getLogger(AddUserHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        User user = (User) to;

        try {
            UserManager manager = getApplicationService().getUserManager();
            response = manager.addUser(user);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        } catch (DuplicateEntryException e) {
            e.showAlert();
        }

        return response;
    }
}