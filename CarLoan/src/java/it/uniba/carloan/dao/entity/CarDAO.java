package it.uniba.carloan.dao.entity;

import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Agency;
import it.uniba.carloan.entity.Car;

import java.util.List;


public interface CarDAO extends GenericDAO<Car, Integer> {
    List<Car> findByAgency(Agency object) throws PersistenceException;
    boolean updateAvailability(Car object) throws PersistenceException;
}
