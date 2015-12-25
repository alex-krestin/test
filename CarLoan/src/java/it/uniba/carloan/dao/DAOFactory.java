package it.uniba.carloan.dao;

import it.uniba.carloan.business.utility.SessionData;
import it.uniba.carloan.dao.entity.*;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.exception.UnknownDatabaseTypeException;
import it.uniba.carloan.dao.mysql.MySQLDAOFactory;
import it.uniba.carloan.dao.sqlite.SQLiteDAOFactory;
import it.uniba.carloan.entity.Category;

public abstract class DAOFactory {

    public static DAOFactory getDAOFactory() throws PersistenceException {

        switch (SessionData.config.getDatabaseType()) {
            case MYSQL:
                return new MySQLDAOFactory();
            case SQLITE:
                return new SQLiteDAOFactory();
            default:
                throw new UnknownDatabaseTypeException();
        }
    }

    public abstract DatabaseConnector getDatabaseConnector();

    public abstract SystemDAO getSystemDAO();
    public abstract AgencyDAO getAgencyDAO();
    public abstract UserDAO getUserDAO();

    public abstract ServiceDAO getServiceDAO();
    public abstract ServiceTariffDAO getServiceTariffDAO();

    public abstract AccessoryDAO getAccessoryDAO();
    public abstract GenericDAO<Category, Integer> getAccessoryCategoryDAO();
    public abstract AccessoryTariffDAO getAccessoryTariffDAO();

    public abstract PenaltyDAO getPenaltyDAO();
    public abstract GenericDAO<Category, Integer> getPenaltyCategoryDAO();
    public abstract PenaltyTariffDAO getPenaltyTariffDAO();

    public abstract CarDAO getCarDAO();
    public abstract GenericDAO<Category, Integer> getCarCategoryDAO();
    public abstract CarTariffDAO getCarTariffDAO();

    public abstract ClientDAO getClientDAO();
    public abstract ContractDAO getContractDAO();
}
