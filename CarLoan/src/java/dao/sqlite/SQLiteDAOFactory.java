package dao.sqlite;

import dao.DAOFactory;
import dao.entity.*;

public class SQLiteDAOFactory extends DAOFactory {

    @Override
    public UserDAO getUserDAO() throws IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    public ServiceDAO getServiceDAO() throws IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    public AccessoryDAO getAccessoryDAO() throws IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    public PenaltyDAO getPenaltyDAO() throws IllegalAccessError, InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public CarDAO getCarDAO() {
        return null;
    }

    @Override
    public ClientDAO getClientDAO() throws InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public ContractDAO getContractDAO() throws InstantiationException, IllegalAccessException {
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
}
