package dao.entity;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import entity.Car;
import entity.Category;

import java.sql.Date;
import java.util.ArrayList;


public interface CarDAO {

    boolean addCar(String brand, String model, int year, int categoryID, int currentAgencyID, String plate, int doors, int passengers, String description, int mileage) throws DuplicateEntryException;
    boolean updateCar(int carID, String brand, String model, int year, int categoryID, String plate, int doors, int passengers, String description) throws DuplicateEntryException;
    boolean deleteCar(int carID) throws MySQLIntegrityConstraintViolationException;
    boolean changeCarStatus(int carID, boolean available);
    ArrayList<Car> getAllCars();
    ArrayList<Car> getAllCars(int agencyID);
    ArrayList<Car> getAllAvailableCars(int agencyID, Date fromDate, Date toDate);
    boolean addCategory(String categoryName, String description) throws DuplicateEntryException;
    boolean updateCategory(int categoryID, String categoryName, String description) throws DuplicateEntryException;
    boolean deleteCategory(int categoryID) throws MySQLIntegrityConstraintViolationException;
    ArrayList<Category> getAllCategories();
}
