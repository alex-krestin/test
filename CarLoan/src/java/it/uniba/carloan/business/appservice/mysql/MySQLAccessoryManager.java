package it.uniba.carloan.business.appservice.mysql;

import it.uniba.carloan.business.appservice.generics.AccessoryManager;
import it.uniba.carloan.business.bo.AccessoryBO;
import it.uniba.carloan.business.bo.AccessoryCategoryBO;
import it.uniba.carloan.business.bo.AccessoryTariffBO;
import it.uniba.carloan.business.utility.TariffDateValidator;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Accessory;
import it.uniba.carloan.entity.AccessoryTariff;
import it.uniba.carloan.entity.Category;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.presentation.helper.exception.InvalidTariffDateException;

import java.util.List;

import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.closeConnection;
import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.getConnection;


public class MySQLAccessoryManager implements AccessoryManager {

    private AccessoryBO accessoryBO;
    private AccessoryCategoryBO accessoryCategoryBO;
    private AccessoryTariffBO accessoryTariffBO;


    public MySQLAccessoryManager() throws PersistenceException {
        getConnection();
        this.accessoryBO = new AccessoryBO();
        this.accessoryCategoryBO = new AccessoryCategoryBO();
        this.accessoryTariffBO = new AccessoryTariffBO();
    }

    @Override
    public Response addAccessory(Accessory accessory) throws DuplicateEntryException, PersistenceException {
        accessoryBO.addAccessory(accessory);
        closeConnection();

        return new Response(true);
    }


    public Response updateAccessory(Accessory accessory) throws DuplicateEntryException, PersistenceException {
        boolean result = accessoryBO.updateAccessory(accessory);
        closeConnection();

        return new Response(result);
    }


    public Response deleteAccessory(Accessory accessory) throws IntegrityConstraintViolationException, PersistenceException {
        boolean result = accessoryBO.deleteAccessory(accessory);
        closeConnection();

        return new Response(result);
    }


    public Response getAllAccessories() throws PersistenceException {
        List<Accessory> result = accessoryBO.getAllAccessories();
        closeConnection();

        return new Response(result);
    }


    public Response changeAccessoryState(Accessory accessory) throws PersistenceException {
        accessory.setAvailable(!accessory.isAvailable());
        boolean result = accessoryBO.updateAccessoryAvailability(accessory);
        closeConnection();

        return new Response(result);
    }



    public Response addCategory(Category category) throws DuplicateEntryException, PersistenceException {
        accessoryCategoryBO.addCategory(category);
        closeConnection();

        return new Response(true);
    }


    public Response updateCategory(Category category) throws DuplicateEntryException, PersistenceException {
        boolean result = accessoryCategoryBO.updateCategory(category);
        closeConnection();

        return new Response(result);
    }


    public Response deleteCategory(Category category) throws IntegrityConstraintViolationException, PersistenceException {
        boolean result = accessoryCategoryBO.deleteCategory(category);
        closeConnection();

        return new Response(result);
    }

    public Response getAllCategories() throws PersistenceException {
        List<Category> result = accessoryCategoryBO.getAllCategories();
        closeConnection();

        return new Response(result);
    }


    public Response addTariff(AccessoryTariff tariff) throws DuplicateEntryException, InvalidTariffDateException, PersistenceException {
        validateTariffDate(tariff);
        accessoryTariffBO.addTariff(tariff);
        closeConnection();

        return new Response(true);
    }

    public Response updateTariff(AccessoryTariff tariff) throws DuplicateEntryException, InvalidTariffDateException, PersistenceException {
        validateTariffDate(tariff);
        boolean result = accessoryTariffBO.updateTariff(tariff);
        closeConnection();

        return new Response(result);
    }

    public Response deleteTariff(AccessoryTariff tariff) throws IntegrityConstraintViolationException, PersistenceException {
        boolean result = accessoryTariffBO.deleteTariff(tariff);
        closeConnection();

        return new Response(result);
    }

    public Response getAllTariffs() throws PersistenceException {
        List<AccessoryTariff> result = accessoryTariffBO.getAllTariffs();
        closeConnection();

        return new Response(result);
    }

    private void validateTariffDate(AccessoryTariff tariff) throws PersistenceException, InvalidTariffDateException {
        List<AccessoryTariff> checkList = accessoryTariffBO.getTariffsByCategory(tariff);
        closeConnection();

        TariffDateValidator<AccessoryTariff> validator = new TariffDateValidator<>();
        validator.validate(tariff, checkList);
    }

}
