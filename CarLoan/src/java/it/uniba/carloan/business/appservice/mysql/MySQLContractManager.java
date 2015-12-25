package it.uniba.carloan.business.appservice.mysql;


import it.uniba.carloan.business.appservice.generics.ContractManager;
import it.uniba.carloan.business.bo.ContractBO;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.*;

public class MySQLContractManager implements ContractManager{

    private ContractBO bo;

    public MySQLContractManager() throws PersistenceException {
        this.bo = new ContractBO();
    }

    public Response getAllAvailableServices(Contract contract) {
        return bo.getAllAvailableServices(contract);
    }

    public Response getAllAvailableAccessories(SearchRequest<Accessory> searchRequest) {
        return bo.getAllAvailableAccessories(searchRequest);
    }

    public Response getAvailableCars(SearchRequest<Car> searchRequest) {
        return bo.getAllAvailableCars(searchRequest);
    }

}
