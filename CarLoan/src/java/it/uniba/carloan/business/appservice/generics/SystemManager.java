package it.uniba.carloan.business.appservice.generics;


import it.uniba.carloan.business.security.DatabaseConfigObject;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Response;

public interface SystemManager {
    Response checkConnection(DatabaseConfigObject object) throws PersistenceException;
}
