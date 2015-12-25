package it.uniba.carloan.business.appservice.generics;


import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.User;

public interface UserManager {
    Response addUser(User user) throws DuplicateEntryException, PersistenceException;
    Response updateUser(User user) throws DuplicateEntryException, PersistenceException;
    Response deleteUser(User user) throws IntegrityConstraintViolationException, PersistenceException;
    Response getAllUsers() throws PersistenceException;
    Response changeUserAccess(User user) throws PersistenceException;
}
