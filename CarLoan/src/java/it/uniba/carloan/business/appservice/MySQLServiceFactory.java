package it.uniba.carloan.business.appservice;


import it.uniba.carloan.business.appservice.generics.*;
import it.uniba.carloan.business.appservice.mysql.*;
import it.uniba.carloan.dao.exception.PersistenceException;

public class MySQLServiceFactory extends ServiceFactory {

    @Override
    public AccessoryManager getAccessoryManager() throws PersistenceException {
        return new MySQLAccessoryManager();
    }

    @Override
    public AgencyManager getAgencyManager() throws PersistenceException {
        return new MySQLAgencyManager();
    }

    @Override
    public CarManager getCarManager() throws PersistenceException {
        return new MySQLCarManager();
    }

    @Override
    public ClientManager getClientManager() throws PersistenceException {
        return new MySQLClientManager();
    }

    @Override
    public ContractManager getContractManager() throws PersistenceException {
        return new MySQLContractManager();
    }

    @Override
    public PenaltyManager getPenaltyManager() throws PersistenceException {
        return new MySQLPenaltyManager();
    }

    @Override
    public ServiceManager getServiceManager() throws PersistenceException {
        return new MySQLServiceManager();
    }

    @Override
    public SystemManager getSystemManager() throws PersistenceException {
        return new MySQLSystemManager();
    }

    @Override
    public UserManager getUserManager() throws PersistenceException {
        return new MySQLUserManager();
    }



}
