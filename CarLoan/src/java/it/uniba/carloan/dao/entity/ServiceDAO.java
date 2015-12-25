package it.uniba.carloan.dao.entity;

import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Service;


public interface ServiceDAO extends GenericDAO<Service, Integer> {
    boolean updateAvailability(Service object) throws PersistenceException;
}
