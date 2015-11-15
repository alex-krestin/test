package business;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.DAOFactory;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import dao.entity.AccessoryDAO;
import entity.Accessory;
import entity.Category;
import entity.TransferObject;
import utility.SessionData;

import java.util.ArrayList;


public class AccessoryManager {
    private AccessoryDAO dao;

    public AccessoryManager() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getAccessoryDAO();
    }

    public TransferObject getAllAccessories() {
        ArrayList<Accessory> accessories = dao.getAllAccessories();
        return new TransferObject(accessories);
    }

    public TransferObject addAccessory(Accessory accessory) throws DuplicateEntryException {
        boolean result;

        result = dao.addAccessory(accessory.getCategory().getId(), accessory.getTitle(), accessory.getDescription(),
                accessory.getInventoryCode(), accessory.getAgency().getAgencyID());

        return new TransferObject(result);
    }

    public TransferObject updateAccessory(Accessory accessory) throws DuplicateEntryException {
        boolean result;

        result = dao.updateAccessory(accessory.getId(), accessory.getCategory().getId(), accessory.getTitle(),
                accessory.getDescription(), accessory.getInventoryCode(), accessory.getAgency().getAgencyID());

        return new TransferObject(result);
    }

    public TransferObject deleteAccessory(Accessory accessory) throws MySQLIntegrityConstraintViolationException {
        boolean result;

        result = dao.deleteAccessory(accessory.getId());

        return new TransferObject(result);
    }

    public TransferObject changeAccessoryState(Accessory accessory) {
        boolean result;

        if(accessory.isAvailable()) {
            accessory.setAvailable(false);
        }
        else {
            accessory.setAvailable(true);
        }
        result = dao.changeAccessoryStatus(accessory.getId(), accessory.isAvailable());
        return new TransferObject(result);
    }

    public TransferObject getAllCategories() {
        ArrayList<Category> categories = dao.getAllCategories();
        return new TransferObject(categories);
    }

    public TransferObject addCategory(Category category) throws DuplicateEntryException {
        boolean result;

        result = dao.addCategory(category.getName(), category.getDescription());

        return new TransferObject(result);
    }

    public TransferObject updateCategory(Category category) throws DuplicateEntryException {
        boolean result;

        result = dao.updateCategory(category.getId(), category.getName(), category.getDescription());

        return new TransferObject(result);
    }

    public TransferObject deleteCategory(Category category) throws MySQLIntegrityConstraintViolationException {
        boolean result;

        result = dao.deleteCategory(category.getId());

        return new TransferObject(result);
    }
}
