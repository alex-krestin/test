package business.appservice;


import business.bo.ContractBO;
import dao.exception.UnknownDatabaseTypeException;
import entity.*;

public class ContractManager {

    private ContractBO bo;

    public ContractManager() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
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
