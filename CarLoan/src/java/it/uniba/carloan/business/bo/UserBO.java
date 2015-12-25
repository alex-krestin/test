package it.uniba.carloan.business.bo;


import it.uniba.carloan.dao.DAOFactory;
import it.uniba.carloan.dao.entity.UserDAO;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.User;

import java.util.List;

public class UserBO {
    private UserDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public UserBO() throws PersistenceException {
        this.dao = DAOFactory.getDAOFactory().getUserDAO();
    }

    public Integer addUser(User user) throws DuplicateEntryException, PersistenceException {
        return dao.add(user);
    }

    public boolean updateUser(User user) throws DuplicateEntryException, PersistenceException {
        return dao.update(user);
    }

    public boolean deleteUser(User user) throws IntegrityConstraintViolationException, PersistenceException {
        return dao.delete(user);
    }

    public List<User> getAllUsers() throws PersistenceException {
        return dao.findAll();
    }

    public boolean updateAccess(User user) throws PersistenceException {
        return dao.updateAccess(user);
    }
}
