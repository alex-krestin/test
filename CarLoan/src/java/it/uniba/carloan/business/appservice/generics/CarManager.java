package it.uniba.carloan.business.appservice.generics;


import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.*;
import it.uniba.carloan.presentation.helper.exception.InvalidTariffDateException;

public interface CarManager {
    Response addCar(Car car) throws DuplicateEntryException, PersistenceException;
    Response updateCar(Car car) throws DuplicateEntryException, PersistenceException;
    Response deleteCar(Car car) throws IntegrityConstraintViolationException, PersistenceException;
    Response getAllCars() throws PersistenceException;
    Response getCarsByAgency(Agency agency) throws PersistenceException;
    Response changeCarAvailability(Car car) throws PersistenceException;
    Response getAllCategories() throws PersistenceException;
    Response addCategory(Category category) throws DuplicateEntryException, PersistenceException;
    Response updateCategory(Category category) throws DuplicateEntryException, PersistenceException;
    Response deleteCategory(Category category) throws IntegrityConstraintViolationException, PersistenceException;
    Response addTariff(CarTariff tariff) throws DuplicateEntryException, InvalidTariffDateException, PersistenceException;
    Response updateTariff(CarTariff tariff) throws DuplicateEntryException, InvalidTariffDateException, PersistenceException;
    Response deleteTariff(CarTariff tariff) throws IntegrityConstraintViolationException, PersistenceException;
    Response getAllTariffs() throws PersistenceException;

}
