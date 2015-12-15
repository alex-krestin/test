package business.bo;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.DAOFactory;
import dao.entity.UserDAO;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.User;
import utility.SessionData;

import java.util.List;

public class UserBO {
    private UserDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public UserBO() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getUserDAO();
    }

    public Response addUser(User user) throws DuplicateEntryException {

        boolean result = dao.addUser(user.getName(), user.getSurname(), user.getUsername(), user.getPassword(),
                user.getAccountType(), user.isAccessGranted(), user.getAgencyID());

        return new Response(result);
    }

    public Response updateUser(User user) throws DuplicateEntryException {

        boolean result = dao.updateUser(user.getId(), user.getName(), user.getSurname(), user.getUsername(),
                user.getAccountType(), user.getAgencyID());

        return new Response(result);
    }

    public Response deleteUser(User user) throws MySQLIntegrityConstraintViolationException {
        boolean result = dao.deleteUser(user.getId());
        return new Response(result);
    }

    public Response getAllUsers() {
        List<User> users = dao.getAllUsers();
        return new Response(users);
    }

    public Response changeUserAccess(User user) {

        boolean result = dao.updateAccess(user.getId(), user.isAccessGranted());
        return new Response(result);
    }
}
