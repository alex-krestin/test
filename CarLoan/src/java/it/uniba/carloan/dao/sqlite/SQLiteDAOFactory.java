package it.uniba.carloan.dao.sqlite;

import it.uniba.carloan.dao.DAOFactory;
import it.uniba.carloan.dao.DatabaseConnector;
import it.uniba.carloan.dao.entity.*;
import it.uniba.carloan.entity.Category;

public class SQLiteDAOFactory extends DAOFactory {


    @Override
    public DatabaseConnector getDatabaseConnector() {
        return null;
    }

    @Override
    public SystemDAO getSystemDAO() {
        return null;
    }

    @Override
    public AgencyDAO getAgencyDAO() {
        return null;
    }

    @Override
    public UserDAO getUserDAO() {
        return null;
    }

    @Override
    public ServiceDAO getServiceDAO() {
        return null;
    }

    @Override
    public ServiceTariffDAO getServiceTariffDAO() {
        return null;
    }

    @Override
    public AccessoryDAO getAccessoryDAO() {
        return null;
    }

    @Override
    public GenericDAO<Category, Integer> getAccessoryCategoryDAO() {
        return null;
    }

    @Override
    public AccessoryTariffDAO getAccessoryTariffDAO() {
        return null;
    }

    @Override
    public PenaltyDAO getPenaltyDAO() {
        return null;
    }

    @Override
    public GenericDAO<Category, Integer> getPenaltyCategoryDAO() {
        return null;
    }

    @Override
    public PenaltyTariffDAO getPenaltyTariffDAO() {
        return null;
    }

    @Override
    public CarDAO getCarDAO() {
        return null;
    }

    @Override
    public GenericDAO<Category, Integer> getCarCategoryDAO() {
        return null;
    }

    @Override
    public CarTariffDAO getCarTariffDAO() {
        return null;
    }

    @Override
    public ClientDAO getClientDAO() {
        return null;
    }

    @Override
    public ContractDAO getContractDAO() {
        return null;
    }
}
