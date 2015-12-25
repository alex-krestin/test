package it.uniba.carloan.dao.entity;


import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.CarTariff;

import java.util.List;

public interface CarTariffDAO extends GenericDAO<CarTariff, Integer> {
    List<CarTariff> findByCategory(CarTariff object) throws PersistenceException;
}
