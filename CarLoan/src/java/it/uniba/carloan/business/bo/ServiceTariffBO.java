package it.uniba.carloan.business.bo;


import it.uniba.carloan.dao.DAOFactory;
import it.uniba.carloan.dao.entity.ServiceTariffDAO;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.ServiceTariff;

import java.util.List;

public class ServiceTariffBO {
    private ServiceTariffDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public ServiceTariffBO() throws PersistenceException {
        this.dao = DAOFactory.getDAOFactory().getServiceTariffDAO();
    }

    public Integer addTariff(ServiceTariff tariff) throws DuplicateEntryException, PersistenceException {
        return dao.add(tariff);
    }

    public boolean updateTariff(ServiceTariff tariff) throws DuplicateEntryException, PersistenceException {
        return dao.update(tariff);
    }

    public boolean deleteTariff(ServiceTariff tariff) throws IntegrityConstraintViolationException, PersistenceException {
        return dao.delete(tariff);
    }

    public List<ServiceTariff> getAllTariffs() throws PersistenceException {
        return dao.findAll();
    }

    public List<ServiceTariff> getTariffsByServiceId(ServiceTariff tariff) throws PersistenceException {
        return dao.findByServiceId(tariff);
    }
}
