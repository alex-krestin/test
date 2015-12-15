package dao.entity;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import entity.Accessory;
import entity.AccessoryTariff;
import entity.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public interface AccessoryDAO {
    boolean addAccessory(int categoryID, String title, String description, String inventoryCode, int currentAgencyID)
            throws DuplicateEntryException;
    boolean updateAccessory(int accessoryID, int categoryID, String title, String description, String inventoryCode)
            throws DuplicateEntryException;
    boolean deleteAccessory(int accessoryID) throws MySQLIntegrityConstraintViolationException;
    boolean updateAccessoryStatus(int accessoryID, boolean available);
    List<Accessory> findAllAccessories();
    boolean addCategory(String categoryName, String description) throws DuplicateEntryException;
    boolean updateCategory(int categoryID, String categoryName, String description) throws DuplicateEntryException;
    boolean deleteCategory(int categoryID) throws MySQLIntegrityConstraintViolationException;
    List<Category> findAllCategories();
    boolean addAccessoryTariff(int categoryID, BigDecimal dailyPrice, int maxDays, LocalDate fromDate, LocalDate toDate) throws DuplicateEntryException;
    boolean updateAccessoryTariff(int tariffID, int categoryID, BigDecimal dailyPrice, int maxDays, LocalDate fromDate, LocalDate toDate) throws DuplicateEntryException;
    boolean deleteAccessoryTariff(int tariffID) throws MySQLIntegrityConstraintViolationException;
    List<AccessoryTariff> findAccessoryTariffs();
    List<AccessoryTariff> findAccessoryTariffsByCategory(int categoryID);
}
