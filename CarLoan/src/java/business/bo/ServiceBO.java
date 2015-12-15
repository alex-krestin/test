package business.bo;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.DAOFactory;
import dao.entity.ServiceDAO;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.Service;
import entity.ServiceTariff;
import entity.Tariff;
import presentation.helper.exception.InvalidTariffDateException;
import utility.SessionData;

import java.util.List;

public class ServiceBO {
    private ServiceDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public ServiceBO() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getServiceDAO();
    }

    public Response getAllServices() {
        List<Service> agencies = dao.findAllServices();
        return new Response(agencies);
    }


    public Response addService(Service service) throws DuplicateEntryException {
        boolean result = dao.addService(service.getTitle(), service.getDescription());
        return new Response(result);
    }

    public Response updateService(Service service) throws DuplicateEntryException {
        boolean result = dao.updateService(service.getId(), service.getTitle(), service.getDescription());
        return new Response(result);
    }

    public Response deleteService(Service service) throws MySQLIntegrityConstraintViolationException {
        boolean result = dao.deleteService(service.getId());
        return new Response(result);
    }

    public Response changeServiceStatus(Service service) {

        boolean result = dao.updateServiceStatus(service.getId(), service.isAvailable());
        return new Response(result);
    }

    public Response addTariff(ServiceTariff tariff) throws InvalidTariffDateException {

        boolean result = dao.addServiceTariff(tariff.getServiceID(), tariff.getPrice(), tariff.getFromDate(), tariff.getToDate());

        return new Response(result);
    }

    public Response updateTariff(ServiceTariff tariff) throws InvalidTariffDateException {

        boolean result = dao.updateServiceTariff(tariff.getTariffID(), tariff.getServiceID(),
                tariff.getPrice(), tariff.getFromDate(), tariff.getToDate());

        return new Response(result);
    }

    public Response deleteTariff(Tariff tariff) throws MySQLIntegrityConstraintViolationException {
        boolean result = dao.deleteServiceTariff(tariff.getTariffID());

        return new Response(result);
    }

    public Response getAllTariffs() {
        List<ServiceTariff> tariffs = dao.findServiceTariffs();
        return new Response(tariffs);
    }

    public List<ServiceTariff> getAllTariffsByID(ServiceTariff tariff) {
        return dao.findServiceTariffsByID(tariff.getServiceID());
    }
}
