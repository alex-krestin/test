package it.uniba.carloan.dao.entity;


import it.uniba.carloan.business.security.DatabaseConfigObject;
import it.uniba.carloan.dao.exception.PersistenceException;

public interface SystemDAO {
    boolean checkConnection(DatabaseConfigObject config) throws PersistenceException;
}
