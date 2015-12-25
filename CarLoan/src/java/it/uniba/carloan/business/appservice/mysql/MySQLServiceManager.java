package it.uniba.carloan.business.appservice.mysql;


import it.uniba.carloan.business.appservice.generics.ServiceManager;
import it.uniba.carloan.business.bo.ServiceBO;
import it.uniba.carloan.business.bo.ServiceTariffBO;
import it.uniba.carloan.business.utility.TariffDateValidator;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.Service;
import it.uniba.carloan.entity.ServiceTariff;
import it.uniba.carloan.presentation.helper.exception.InvalidTariffDateException;

import java.util.List;

import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.closeConnection;
import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.getConnection;


public class MySQLServiceManager implements ServiceManager {
    private ServiceBO serviceBO;
    private ServiceTariffBO serviceTariffBO;

    public MySQLServiceManager() throws PersistenceException {
        getConnection();
        this.serviceBO = new ServiceBO();
        this.serviceTariffBO = new ServiceTariffBO();
    }

    public Response addService(Service service) throws DuplicateEntryException, PersistenceException {
        serviceBO.addService(service);
        closeConnection();

        return new Response(true);
    }

    public Response updateService(Service service) throws DuplicateEntryException, PersistenceException {
        boolean result = serviceBO.updateService(service);
        closeConnection();

        return new Response(result);
    }

    public Response deleteService(Service service) throws IntegrityConstraintViolationException, PersistenceException {
        boolean result = serviceBO.deleteService(service);
        closeConnection();

        return new Response(result);
    }

    public Response getAllServices() throws PersistenceException {
        List<Service> result = serviceBO.getAllServices();
        closeConnection();

        return new Response(result);
    }

    public Response changeServiceStatus(Service service) throws PersistenceException {
        service.setAvailable(!service.isAvailable());
        boolean result = serviceBO.updateServiceAvailability(service);
        closeConnection();

        return new Response(result);
    }

    public Response addTariff(ServiceTariff tariff) throws InvalidTariffDateException, DuplicateEntryException, PersistenceException {
        validateTariffDate(tariff);
        serviceTariffBO.addTariff(tariff);
        closeConnection();

        return new Response(true);
    }

    public Response updateTariff(ServiceTariff tariff) throws InvalidTariffDateException, DuplicateEntryException, PersistenceException {
        validateTariffDate(tariff);
        boolean result = serviceTariffBO.updateTariff(tariff);
        closeConnection();

        return new Response(result);
    }

    public Response deleteTariff(ServiceTariff tariff) throws IntegrityConstraintViolationException, PersistenceException {
        boolean result = serviceTariffBO.deleteTariff(tariff);
        closeConnection();

        return new Response(result);
    }

    public Response getAllTariffs() throws PersistenceException {
        List<ServiceTariff> result = serviceTariffBO.getAllTariffs();
        closeConnection();

        return new Response(result);
    }

    private void validateTariffDate(ServiceTariff tariff) throws PersistenceException, InvalidTariffDateException {
        List<ServiceTariff> checkList = serviceTariffBO.getTariffsByServiceId(tariff);
        closeConnection();

        TariffDateValidator<ServiceTariff> validator = new TariffDateValidator<>();
        validator.validate(tariff, checkList);
    }
}
