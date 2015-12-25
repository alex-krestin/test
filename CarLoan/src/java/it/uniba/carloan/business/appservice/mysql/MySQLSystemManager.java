package it.uniba.carloan.business.appservice.mysql;

import it.uniba.carloan.business.appservice.generics.SystemManager;
import it.uniba.carloan.business.bo.SystemBO;
import it.uniba.carloan.business.security.DatabaseConfigObject;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Response;


public class MySQLSystemManager implements SystemManager {

    private SystemBO bo;

    public MySQLSystemManager() throws PersistenceException {
        this.bo = new SystemBO();
    }

    public Response checkConnection(DatabaseConfigObject object) throws PersistenceException {
        return new Response(bo.checkConnection(object));
    }
}
