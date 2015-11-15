package dao;

import dao.exception.UnknownDatabaseTypeException;
import dao.mysql.MySQLDAOFactory;
import dao.sqlite.SQLiteDAOFactory;
import dao.entity.*;
import utility.Constants;

public abstract class DAOFactory {

    public static DAOFactory getDAOFactory(String whichFactory) throws UnknownDatabaseTypeException {

        switch (whichFactory) {
            case Constants.MYSQL:
                return new MySQLDAOFactory();
            case Constants.SQLITE:
                return new SQLiteDAOFactory();
            default:
                throw new UnknownDatabaseTypeException();
        }
    }

    public abstract SystemDAO getSystemDAO() throws IllegalAccessException, InstantiationException;
    public abstract AgencyDAO getAgencyDAO() throws IllegalAccessException, InstantiationException;
    public abstract UserDAO getUserDAO() throws IllegalAccessException, InstantiationException;
    public abstract ServiceDAO getServiceDAO() throws IllegalAccessException, InstantiationException;
    public abstract AccessoryDAO getAccessoryDAO() throws IllegalAccessException, InstantiationException;
    public abstract PenaltyDAO getPenaltyDAO() throws InstantiationException, IllegalAccessException;
    public abstract CarDAO getCarDAO() throws InstantiationException, IllegalAccessException;
    public abstract ClientDAO getClientDAO() throws InstantiationException, IllegalAccessException;

    protected static Object newDao(Class c) throws InstantiationException, IllegalAccessException {
        return c.newInstance();
    }
}
