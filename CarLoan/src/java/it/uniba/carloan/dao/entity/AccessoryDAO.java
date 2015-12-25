package it.uniba.carloan.dao.entity;

import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Accessory;


public interface AccessoryDAO extends GenericDAO<Accessory, Integer> {
    boolean updateAvailability(Accessory object) throws PersistenceException;
}
