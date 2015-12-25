package it.uniba.carloan.business.appservice.generics;


import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.Service;
import it.uniba.carloan.entity.ServiceTariff;
import it.uniba.carloan.presentation.helper.exception.InvalidTariffDateException;

public interface ServiceManager {
    Response addService(Service service) throws DuplicateEntryException, PersistenceException;
    Response updateService(Service service) throws DuplicateEntryException, PersistenceException;
    Response deleteService(Service service) throws IntegrityConstraintViolationException, PersistenceException;
    Response getAllServices() throws PersistenceException;
    Response changeServiceStatus(Service service) throws PersistenceException;
    Response addTariff(ServiceTariff tariff) throws InvalidTariffDateException, DuplicateEntryException, PersistenceException;
    Response updateTariff(ServiceTariff tariff) throws InvalidTariffDateException, DuplicateEntryException, PersistenceException;
    Response deleteTariff(ServiceTariff tariff) throws IntegrityConstraintViolationException, PersistenceException;
    Response getAllTariffs() throws PersistenceException;

}
