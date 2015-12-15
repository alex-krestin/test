package business.bo;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.DAOFactory;
import dao.entity.AccessoryDAO;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import dao.mysql.MySQLAccessoryDAO;
import entity.*;
import presentation.helper.exception.InvalidTariffDateException;
import utility.SessionData;

import java.util.List;

public class AccessoryBO {
    private AccessoryDAO dao;

    /**
     * Instantiates a new Accessory Business Object. //TODO add description
     *
     * @throws UnknownDatabaseTypeException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @see DAOFactory
     */
    @SuppressWarnings("LawOfDemeter")
    public AccessoryBO() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getAccessoryDAO();
    }

    /**
     * Sends request to DAO in order to get all accessories.
     *
     * @return <code>TransferObject</code> with accessories list
     * @see MySQLAccessoryDAO#findAllAccessories()
     */
    public Response getAllAccessories() {
        List<Accessory> accessories = dao.findAllAccessories();
        return new Response(accessories);
    }

    /**
     * Sends request to DAO in order to add a new accessory.
     *
     * @param accessory an accessory to be added
     * @return <code>TransferObject</code> with operation result
     * @throws DuplicateEntryException if accessory inventory code isn't unique
     * @see MySQLAccessoryDAO#addAccessory(int, String, String, String, int)
     */
    public Response addAccessory(Accessory accessory) throws DuplicateEntryException {

        boolean result = dao.addAccessory(accessory.getCategoryID(), accessory.getTitle(), accessory.getDescription(),
                accessory.getInventoryCode(), accessory.getCurrentAgencyID());

        return new Response(result);
    }

    /**
     * Sends request to DAO in order to update accessory description.
     *
     * @param accessory an accessory to be updated
     * @return <code>TransferObject</code> with operation result
     * @throws DuplicateEntryException if accessory inventory code isn't unique
     * @see AccessoryDAO#updateAccessory(int, int, String, String, String)
     */
    public Response updateAccessory(Accessory accessory) throws DuplicateEntryException {

        boolean result = dao.updateAccessory(accessory.getId(), accessory.getCategoryID(), accessory.getTitle(),
                accessory.getDescription(), accessory.getInventoryCode());

        return new Response(result);
    }

    /**
     * Sends request to DAO in order to delete an accessory.
     *
     * @param accessory an accessory to be deleted
     * @return <code>TransferObject</code> with operation result
     * @throws MySQLIntegrityConstraintViolationException if accessory is used by another record
     * @see AccessoryDAO#deleteAccessory(int)
     */
    public Response deleteAccessory(Accessory accessory) throws MySQLIntegrityConstraintViolationException {

        boolean result = dao.deleteAccessory(accessory.getId());

        return new Response(result);
    }

    /**
     * Sends request to DAO in order to change accessory state to "archived" if it couldn't be deleted.
     *
     * @param accessory an accessory to be archived
     * @return <code>TransferObject</code> with operation result
     * @see AccessoryDAO#updateAccessoryStatus(int, boolean)
     */
    public Response changeAccessoryState(Accessory accessory) {

        boolean result = dao.updateAccessoryStatus(accessory.getId(), accessory.isAvailable());

        return new Response(result);
    }

    /**
     * Sends request to DAO in order to get all accessory categories.
     *
     * @return <code>TransferObject</code> with accessory categories list
     */
    public Response getAllCategories() {

        List<Category> categories = dao.findAllCategories();

        return new Response(categories);
    }

    /**
     * Add category transfer object. //TODO
     *
     * @param category the category
     * @return the transfer object
     * @throws DuplicateEntryException the duplicate entry exception
     */
    public Response addCategory(Category category) throws DuplicateEntryException {

        boolean result = dao.addCategory(category.getName(), category.getDescription());

        return new Response(result);
    }

    /**
     * Update category transfer object. //TODO
     *
     * @param category the category
     * @return the transfer object
     * @throws DuplicateEntryException the duplicate entry exception
     */
    public Response updateCategory(Category category) throws DuplicateEntryException {

        boolean result = dao.updateCategory(category.getId(), category.getName(), category.getDescription());

        return new Response(result);
    }

    /**
     * Delete category transfer object. //TODO
     *
     * @param category the category
     * @return the transfer object
     * @throws MySQLIntegrityConstraintViolationException the my sql integrity addConstraint violation exception
     */
    public Response deleteCategory(Category category) throws MySQLIntegrityConstraintViolationException {

        boolean result = dao.deleteCategory(category.getId());

        return new Response(result);
    }

    public Response addTariff(AccessoryTariff tariff) throws DuplicateEntryException, InvalidTariffDateException {

        boolean result = dao.addAccessoryTariff(tariff.getCategoryID(), tariff.getDailyPrice(),
                tariff.getMaxDays(), tariff.getFromDate(), tariff.getToDate());

        return new Response(result);
    }

    public Response updateTariff(AccessoryTariff tariff) throws DuplicateEntryException, InvalidTariffDateException {

        boolean result = dao.updateAccessoryTariff(tariff.getTariffID(), tariff.getCategoryID(),
                tariff.getDailyPrice(), tariff.getMaxDays(), tariff.getFromDate(), tariff.getToDate());

        return new Response(result);
    }

    public Response deleteTariff(Tariff tariff) throws MySQLIntegrityConstraintViolationException {

        boolean result = dao.deleteAccessoryTariff(tariff.getTariffID());

        return new Response(result);
    }

    public Response getAllTariffs() {

        List<AccessoryTariff> tariffs = dao.findAccessoryTariffs();

        return new Response(tariffs);
    }

    public List<AccessoryTariff> getAllTariffsByCategory(AccessoryTariff tariff) {
        return dao.findAccessoryTariffsByCategory(tariff.getCategoryID());
    }

}
