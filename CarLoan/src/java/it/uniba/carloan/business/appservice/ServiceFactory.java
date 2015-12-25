package it.uniba.carloan.business.appservice;



import it.uniba.carloan.business.appservice.generics.*;
import it.uniba.carloan.business.utility.SessionData;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.exception.UnknownDatabaseTypeException;

public abstract class ServiceFactory {

    public static ServiceFactory getApplicationService() throws PersistenceException {
        switch (SessionData.config.getDatabaseType()) {
            case MYSQL:
                return new MySQLServiceFactory();
            default:
                throw new UnknownDatabaseTypeException();
        }
    }

    public abstract AccessoryManager getAccessoryManager() throws PersistenceException;
    public abstract AgencyManager getAgencyManager() throws PersistenceException;
    public abstract CarManager getCarManager() throws PersistenceException;
    public abstract ClientManager getClientManager() throws PersistenceException;
    public abstract ContractManager getContractManager() throws PersistenceException;
    public abstract PenaltyManager getPenaltyManager() throws PersistenceException;
    public abstract ServiceManager getServiceManager() throws PersistenceException;
    public abstract SystemManager getSystemManager() throws PersistenceException;
    public abstract UserManager getUserManager() throws PersistenceException;


}
