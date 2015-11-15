package dao.entity;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import entity.Accessory;
import entity.Category;

import java.util.ArrayList;


public interface AccessoryDAO {
    boolean addAccessory(int categoryID, String title, String description, String inventoryCode, int currentAgencyID)
            throws DuplicateEntryException;
    boolean updateAccessory(int accessoryID, int categoryID, String title, String description, String inventoryCode, int currentAgencyID)
            throws DuplicateEntryException;
    boolean deleteAccessory(int accessoryID) throws MySQLIntegrityConstraintViolationException;
    boolean changeAccessoryStatus(int accessoryID, boolean available);
    ArrayList<Accessory> getAllAccessories();
    boolean addCategory(String categoryName, String description) throws DuplicateEntryException;
    boolean updateCategory(int categoryID, String categoryName, String description) throws DuplicateEntryException;
    boolean deleteCategory(int categoryID) throws MySQLIntegrityConstraintViolationException;
    ArrayList<Category> getAllCategories();
}
