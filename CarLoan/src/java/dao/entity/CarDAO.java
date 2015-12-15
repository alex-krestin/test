package dao.entity;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import entity.Car;
import entity.CarTariff;
import entity.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public interface CarDAO {

    boolean addCar(String brand, String model, int year, int categoryID, int currentAgencyID, String plate, int doors, int passengers, String description, int mileage) throws DuplicateEntryException;
    boolean updateCar(int carID, String brand, String model, int year, int categoryID, String plate, int doors, int passengers, String description) throws DuplicateEntryException;
    boolean deleteCar(int carID) throws MySQLIntegrityConstraintViolationException;
    boolean updateCarStatus(int carID, boolean available);
    List<Car> findAllCars();
    List<Car> findCarsByAgency(int agencyID);
    boolean addCategory(String categoryName, String description) throws DuplicateEntryException;
    boolean updateCategory(int categoryID, String categoryName, String description) throws DuplicateEntryException;
    boolean deleteCategory(int categoryID) throws MySQLIntegrityConstraintViolationException;
    List<Category> findAllCategories();
    boolean addCarTariff(int categoryID, BigDecimal dailyPrice, BigDecimal weeklyPrice, BigDecimal kmPrice,
                         BigDecimal freeKmPrice, LocalDate fromDate, LocalDate toDate) throws DuplicateEntryException;
    boolean updateCarTariff(int tariffID, int categoryID, BigDecimal dailyPrice, BigDecimal weeklyPrice, BigDecimal kmPrice,
                            BigDecimal freeKmPrice, LocalDate fromDate, LocalDate toDate) throws DuplicateEntryException;
    boolean deleteCarTariff(int tariffID) throws MySQLIntegrityConstraintViolationException;
    List<CarTariff> findCarTariffs();
    List<CarTariff> findCarTariffsByCategory(int categoryID);
}
