package presentation.helper.collection.users;

import business.UserManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.TransferObject;
import entity.User;
import presentation.helper.Helper;

public class DeleteUserHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // Create Default Transfer Object
        TransferObject responce = new TransferObject();
        // cast TO to needed object
        User user = (User) to;

        try {
            UserManager userManager = new UserManager();
            responce = userManager.deleteUser(user);
        } catch (UnknownDatabaseTypeException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return responce;
    }

}
