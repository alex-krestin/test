package it.uniba.carloan.business.bo;


import it.uniba.carloan.dao.DAOFactory;
import it.uniba.carloan.dao.entity.ServiceDAO;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Service;

import java.util.List;

public class ServiceBO {
    private ServiceDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public ServiceBO() throws PersistenceException {
        this.dao = DAOFactory.getDAOFactory().getServiceDAO();
    }

    public Integer addService(Service service) throws DuplicateEntryException, PersistenceException {
        return dao.add(service);
    }

    public boolean updateService(Service service) throws DuplicateEntryException, PersistenceException {
        return dao.update(service);
    }

    public boolean deleteService(Service service) throws IntegrityConstraintViolationException, PersistenceException {
        return dao.delete(service);
    }

    public List<Service> getAllServices() throws PersistenceException {
        return dao.findAll();
    }

    public boolean updateServiceAvailability(Service service) throws PersistenceException {
        return dao.updateAvailability(service);
    }
}
