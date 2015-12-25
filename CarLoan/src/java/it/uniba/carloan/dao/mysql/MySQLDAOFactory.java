package it.uniba.carloan.dao.mysql;

import it.uniba.carloan.dao.DAOFactory;
import it.uniba.carloan.dao.DatabaseConnector;
import it.uniba.carloan.dao.entity.*;
import it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector;
import it.uniba.carloan.entity.Category;


public class MySQLDAOFactory extends DAOFactory {

    @Override
    public DatabaseConnector getDatabaseConnector() {
        return new MySQLDatabaseConnector();
    }

    @Override
    public UserDAO getUserDAO() {
        return new MySQLUserDAO();
    }

    @Override
    public ServiceDAO getServiceDAO() {
        return new MySQLServiceDAO();
    }

    @Override
    public ServiceTariffDAO getServiceTariffDAO() {
        return new MySQLServiceTariffDAO();
    }

    @Override
    public AccessoryDAO getAccessoryDAO() {
        return new MySQLAccessoryDAO();
    }

    @Override
    public AccessoryTariffDAO getAccessoryTariffDAO() {
        return new MySQLAccessoryTariffDAO();
    }

    @Override
    public GenericDAO<Category, Integer> getAccessoryCategoryDAO() {
        return new MySQLAccessoryCategoryDAO();
    }

    @Override
    public PenaltyDAO getPenaltyDAO() {
        return new MySQLPenaltyDAO();
    }

    @Override
    public GenericDAO<Category, Integer> getPenaltyCategoryDAO() {
        return new MySQLPenaltyCategoryDAO();
    }

    @Override
    public PenaltyTariffDAO getPenaltyTariffDAO() {
        return new MySQLPenaltyTariffDAO();
    }

    @Override
    public CarDAO getCarDAO() {
        return new MySQLCarDAO();
    }

    @Override
    public GenericDAO<Category, Integer> getCarCategoryDAO() {
        return new MySQLCarCategoryDAO();
    }

    @Override
    public CarTariffDAO getCarTariffDAO() {
        return new MySQLCarTariffDAO();
    }

    @Override
    public ClientDAO getClientDAO() {
        return new MySQLClientDAO();
    }

    @Override
    public ContractDAO getContractDAO() {
        return new MySQLContractDAO();
    }

    @Override
    public SystemDAO getSystemDAO() {
        return new MySQLSystemDAO();
    }

    @Override
    public AgencyDAO getAgencyDAO() {
        return new MySQLAgencyDAO();
    }
}
