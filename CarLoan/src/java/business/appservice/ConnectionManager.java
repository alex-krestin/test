package business.appservice;

import business.bo.SystemBO;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import utility.DBConfigObject;


public class ConnectionManager {

    private SystemBO bo;

    public ConnectionManager() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.bo = new SystemBO();
    }

    public Response checkConnection(DBConfigObject config) {
        return bo.checkConnection(config);
    }
}
