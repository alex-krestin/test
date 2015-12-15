package business.bo;

import dao.DAOFactory;
import dao.entity.ContractDAO;
import dao.exception.UnknownDatabaseTypeException;
import entity.*;
import utility.SessionData;

import java.util.List;


public class ContractBO {
    private ContractDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public ContractBO() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getContractDAO();
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
