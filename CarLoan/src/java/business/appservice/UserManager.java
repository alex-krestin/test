package business.appservice;

import business.bo.UserBO;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.User;
import utility.MD5;


public class UserManager {
    private UserBO bo;

    public UserManager() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.bo = new UserBO();
    }

    public Response addUser(User user) throws DuplicateEntryException {

        String password = MD5.getMD5(user.getPassword());
        user.setPassword(password);

        return bo.addUser(user);
    }

    public Response updateUser(User user) throws DuplicateEntryException {
        return bo.updateUser(user);
    }

    public Response deleteUser(User user) throws MySQLIntegrityConstraintViolationException {
        return bo.deleteUser(user);
    }

    public Response getAllUsers() {
        return bo.getAllUsers();
    }

    public Response changeUserAccess(User user) {

        if (user.isAccessGranted()) {
            user.setAccessGranted(false);
        }
        else {
            user.setAccessGranted(true);
        }

        return bo.changeUserAccess(user);
    }
}
