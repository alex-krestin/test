package business;

import dao.DAOFactory;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import dao.entity.UserDAO;
import entity.TransferObject;
import entity.User;
import utility.SessionData;
import utility.MD5;

import java.util.ArrayList;


public class UserManager {
    private UserDAO dao;

    public UserManager() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getUserDAO();
    }

    public TransferObject addUser(User user) throws DuplicateEntryException {
        boolean result;

        String password = MD5.getMD5(user.getPassword());

        result = dao.addUser(user.getName(), user.getSurname(), user.getUsername(), password,
                user.getAccountType(), user.isAccessGranted(), user.getAgency().getAgencyID());

        return new TransferObject(result);
    }

    public TransferObject updateUser(User user) throws DuplicateEntryException {
        boolean result;

        result = dao.updateUser(user.getId(), user.getName(), user.getSurname(), user.getUsername(),
                user.getAccountType(), user.getAgency().getAgencyID());

        return new TransferObject(result);
    }

    public TransferObject deleteUser(User user) {
        boolean result;
        result = dao.deleteUser(user.getId());
        return new TransferObject(result);
    }

    public TransferObject getAllUsers() {
        ArrayList<User> users = dao.getAllUsers();
        return new TransferObject(users);
    }

    public TransferObject changeUserAccess(User user) {
        boolean result;

        if (user.isAccessGranted()) {
            user.setAccessGranted(false);
        }
        else {
            user.setAccessGranted(true);
        }

        result = dao.changeAccess(user.getId(), user.isAccessGranted());
        return new TransferObject(result);
    }
}
