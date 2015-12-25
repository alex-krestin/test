package it.uniba.carloan.dao.entity;


import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.ServiceTariff;

import java.util.List;

public interface ServiceTariffDAO extends GenericDAO<ServiceTariff, Integer> {
    List<ServiceTariff> findByServiceId(ServiceTariff object) throws PersistenceException;
}
