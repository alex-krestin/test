package business.appservice;

import business.DateChecker;
import business.bo.AccessoryBO;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.*;
import presentation.helper.exception.InvalidTariffDateException;

import java.time.LocalDate;
import java.util.List;


/**
 * The type Accessory manager.
 */
public class AccessoryManager {

    private AccessoryBO bo;

    /**
     * Instantiates a new Accessory manager. //TODO add description
     *
     * @throws UnknownDatabaseTypeException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public AccessoryManager() throws InstantiationException, IllegalAccessException, UnknownDatabaseTypeException {
        this.bo = new AccessoryBO();
    }


    public Response getAllAccessories() {
        return bo.getAllAccessories();
    }


    public Response addAccessory(Accessory accessory) throws DuplicateEntryException {
        return bo.addAccessory(accessory);
    }


    public Response updateAccessory(Accessory accessory) throws DuplicateEntryException {
        return bo.updateAccessory(accessory);
    }


    public Response deleteAccessory(Accessory accessory) throws MySQLIntegrityConstraintViolationException {
        return bo.deleteAccessory(accessory);
    }


    public Response changeAccessoryState(Accessory accessory) {

        if(accessory.isAvailable()) {
            accessory.setAvailable(false);
        }
        else {
            accessory.setAvailable(true);
        }

        return bo.changeAccessoryState(accessory);
    }


    public Response getAllCategories() {
        return bo.getAllCategories();
    }


    public Response addCategory(Category category) throws DuplicateEntryException {
        return bo.addCategory(category);
    }


    public Response updateCategory(Category category) throws DuplicateEntryException {
        return bo.updateCategory(category);
    }


    public Response deleteCategory(Category category) throws MySQLIntegrityConstraintViolationException {
        return bo.deleteCategory(category);
    }

    public Response addTariff(AccessoryTariff tariff) throws DuplicateEntryException, InvalidTariffDateException {
        if (isInvalidTariffDate(tariff)) {
            throw new InvalidTariffDateException();
        }

        return bo.addTariff(tariff);
    }

    public Response updateTariff(AccessoryTariff tariff) throws DuplicateEntryException, InvalidTariffDateException {
        if (isInvalidTariffDate(tariff)) {
            throw new InvalidTariffDateException();
        }

        return bo.updateTariff(tariff);
    }

    public Response deleteTariff(Tariff tariff) throws MySQLIntegrityConstraintViolationException {
        return bo.deleteTariff(tariff);
    }

    public Response getAllTariffs() {
        return bo.getAllTariffs();
    }

    private boolean isInvalidTariffDate(AccessoryTariff tariff) {
        boolean overlap = false;
        List<AccessoryTariff> tariffs = bo.getAllTariffsByCategory(tariff);

        LocalDate testFromDate = tariff.getFromDate();
        LocalDate testToDate = tariff.getToDate();

        for (Tariff record: tariffs) {
            overlap = DateChecker.checkDates(record.getFromDate(), record.getToDate(), testFromDate, testToDate);
            if(overlap) break;
        }

        return overlap;
    }
}
