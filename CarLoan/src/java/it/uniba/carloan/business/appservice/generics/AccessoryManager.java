package it.uniba.carloan.business.appservice.generics;


import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Accessory;
import it.uniba.carloan.entity.AccessoryTariff;
import it.uniba.carloan.entity.Category;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.presentation.helper.exception.InvalidTariffDateException;

public interface AccessoryManager {
    Response addAccessory(Accessory accessory) throws DuplicateEntryException, PersistenceException;
    Response updateAccessory(Accessory accessory) throws DuplicateEntryException, PersistenceException;
    Response deleteAccessory(Accessory accessory) throws IntegrityConstraintViolationException, PersistenceException;
    Response getAllAccessories() throws PersistenceException;
    Response changeAccessoryState(Accessory accessory) throws PersistenceException;
    Response addCategory(Category category) throws DuplicateEntryException, PersistenceException;
    Response updateCategory(Category category) throws DuplicateEntryException, PersistenceException;
    Response deleteCategory(Category category) throws IntegrityConstraintViolationException, PersistenceException;
    Response getAllCategories() throws PersistenceException;
    Response addTariff(AccessoryTariff tariff) throws DuplicateEntryException,
            InvalidTariffDateException, PersistenceException;
    Response updateTariff(AccessoryTariff tariff) throws DuplicateEntryException,
            InvalidTariffDateException, PersistenceException;
    Response deleteTariff(AccessoryTariff tariff) throws IntegrityConstraintViolationException, PersistenceException;
    Response getAllTariffs() throws PersistenceException;
}
