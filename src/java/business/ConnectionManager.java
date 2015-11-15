package business;

import dao.DAOFactory;
import dao.exception.UnknownDatabaseTypeException;
import dao.entity.SystemDAO;
import entity.TransferObject;
import utility.DBConfigObject;
import utility.SessionData;


public class ConnectionManager {
    private SystemDAO dao;

    public ConnectionManager() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getSystemDAO();
    }

    public TransferObject checkConnection(DBConfigObject config) {
        return new TransferObject(dao.checkConnection(config));
    }
}
