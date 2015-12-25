package it.uniba.carloan.business.appservice.mysql;

import it.uniba.carloan.business.appservice.generics.UserManager;
import it.uniba.carloan.business.bo.UserBO;
import it.uniba.carloan.business.security.MD5;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.User;

import java.util.List;

import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.closeConnection;
import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.getConnection;


public class MySQLUserManager implements UserManager {
    private UserBO bo;

    public MySQLUserManager() throws PersistenceException {
        getConnection();
        this.bo = new UserBO();
    }

    public Response addUser(User user) throws DuplicateEntryException, PersistenceException {

        String password = MD5.getMD5(user.getPassword());
        user.setPassword(password);
        bo.addUser(user);
        closeConnection();

        return new Response(true);
    }

    public Response updateUser(User user) throws DuplicateEntryException, PersistenceException {
        boolean result = bo.updateUser(user);
        closeConnection();

        return new Response(result);
    }

    public Response deleteUser(User user) throws IntegrityConstraintViolationException, PersistenceException {
        boolean result = bo.deleteUser(user);
        closeConnection();

        return new Response(result);
    }

    public Response getAllUsers() throws PersistenceException {
        List<User> result = bo.getAllUsers();
        closeConnection();

        return new Response(result);
    }

    public Response changeUserAccess(User user) throws PersistenceException {
        user.setAccessGranted(!user.isAccessGranted());
        boolean result = bo.updateAccess(user);
        closeConnection();

        return new Response(result);
    }
}
