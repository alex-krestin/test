package it.uniba.carloan.dao.entity;

import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.User;


public interface UserDAO extends GenericDAO<User, Integer> {
    boolean updatePassword(User object) throws PersistenceException;
    boolean updateAccess(User object) throws PersistenceException;
}
