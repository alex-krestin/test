package it.uniba.carloan.dao.entity;


import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.AccessoryTariff;

import java.util.List;

public interface AccessoryTariffDAO extends GenericDAO<AccessoryTariff, Integer> {
    List<AccessoryTariff> findByCategory(AccessoryTariff object) throws PersistenceException;
}
