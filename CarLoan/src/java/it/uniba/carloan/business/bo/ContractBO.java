package it.uniba.carloan.business.bo;

import it.uniba.carloan.dao.DAOFactory;
import it.uniba.carloan.dao.entity.ContractDAO;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.*;

import java.util.List;


public class ContractBO {
    private ContractDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public ContractBO() throws PersistenceException {
        dao = DAOFactory.getDAOFactory().getContractDAO();
    }

    public Response getAllAvailableCars(SearchRequest<Car> searchRequest) {

        List<ContractObject> cars = dao.findAvailableCarsByAgency(searchRequest.getObject().getCurrentAgencyID(),
                searchRequest.getObject().getCategoryID(), searchRequest.getDepartureDateTime(),
                searchRequest.getArrivalDateTime());

        return new Response(cars);
    }

    public Response getAllAvailableAccessories(SearchRequest<Accessory> searchRequest) {

        List<ContractObject> accessories =
                dao.findAvailableAccessoriesByAgencyAndCategory(searchRequest.getObject().getCurrentAgencyID(),
                        searchRequest.getObject().getCategoryID(), searchRequest.getDepartureDateTime(),
                        searchRequest.getArrivalDateTime());

        return new Response(accessories);
    }

    public Response getAllAvailableServices(Contract contract) { //TODO change to ContractObject<Service>

        List<ContractObject> services = dao.findAvailableServices(contract.getDepartureDate());

        return new Response(services);
    }
}
