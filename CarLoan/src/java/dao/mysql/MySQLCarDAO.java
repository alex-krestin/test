package dao.mysql;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.entity.CarDAO;
import dao.exception.DuplicateEntryException;
import entity.Agency;
import entity.Car;
import entity.CarTariff;
import entity.Category;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static dao.mysql.MySQLDAOFactory.*;

@SuppressWarnings("LawOfDemeter")
public class MySQLCarDAO extends MySQLCommonQueries implements CarDAO {
    private static final Logger log = Logger.getLogger(MySQLCarDAO.class.getName());

    @Override
    public boolean addCar(String brand, String model, int year, int categoryID, int currentAgencyID, String plate,
                          int doors, int passengers, String description, int mileage) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;

        String query = "INSERT INTO cars (brand, model, manufacture_year, plate, categoryID, doors, passengers, description, mileage, currentAgencyID)  "
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, brand);
            statement.setString(2, model);
            statement.setInt(3, year);
            statement.setString(4, plate);
            statement.setInt(5, categoryID);
            statement.setInt(6, doors);
            statement.setInt(7, passengers);
            statement.setString(8, description);
            statement.setInt(9, mileage);
            statement.setInt(10, currentAgencyID);


            if (statement.executeUpdate() == 1)
                result = true;

        } catch (MySQLIntegrityConstraintViolationException e) {
            throw new DuplicateEntryException(e);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public boolean updateCar(int carID, String brand, String model, int year, int categoryID, String plate, int doors,
                             int passengers, String description) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;
        String query = "UPDATE cars SET brand=?, model=?, manufacture_year=?, plate=?, categoryID=?, doors=?, passengers=?, description=? WHERE carID=?";

        try {
            statement = conn.prepareStatement(query);
            statement = conn.prepareStatement(query);
            statement.setString(1, brand);
            statement.setString(2, model);
            statement.setInt(3, year);
            statement.setString(4, plate);
            statement.setInt(5, categoryID);
            statement.setInt(6, doors);
            statement.setInt(7, passengers);
            statement.setString(8, description);
            statement.setInt(9, carID);

            if (statement.executeUpdate() == 1)
                result = true;

        } catch (MySQLIntegrityConstraintViolationException e) {
            throw new DuplicateEntryException(e);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public boolean deleteCar(int carID) throws MySQLIntegrityConstraintViolationException {
        return deleteRecord("cars", "carID", carID);
    }

    @Override
    public boolean updateCarStatus(int carID, boolean available) {
        return updateBooleanField("cars", "available", "carID", carID, available);
    }

    @Override
    public List<Car> findAllCars() {
        List<Car> result = new ArrayList<>();
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM cars, car_categories, agencies WHERE cars.categoryID = car_categories.categoryID " +
                "AND cars.currentAgencyID = agencies.agencyID AND available IS TRUE";

        try {
            statement = conn.prepareStatement(query);

            if (statement.execute()) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    Category category = new Category.Builder(rs.getString("categoryName"))
                            .id(rs.getInt("categoryID")).build();

                    Agency agency = new Agency.Builder()
                            .id(rs.getInt("currentAgencyID"))
                            .agencyCode(rs.getString("agencyCode")).build();

                    Car car = new Car.Builder()
                            .carID(rs.getInt("carID"))
                            .brand(rs.getString("brand"))
                            .model(rs.getString("model"))
                            .year(rs.getInt("manufacture_year"))
                            .plate(rs.getString("plate"))
                            .doors(rs.getInt("doors"))
                            .passengers(rs.getInt("passengers"))
                            .description(rs.getString("description"))
                            .mileage(rs.getInt("mileage"))
                            .currentAgency(agency)
                            .category(category).build();

                    result.add(car);
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public List<Car> findCarsByAgency(int agencyID) {
        List<Car> result = new ArrayList<>();
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM cars, car_categories, agencies WHERE cars.categoryID = car_categories.categoryID " +
                "AND currentAgencyID = ? AND cars.currentAgencyID = agencies.agencyID AND available IS TRUE";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, agencyID);

            if (statement.execute()) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    Agency agency = new Agency.Builder()
                            .id(agencyID)
                            .agencyCode(rs.getString("agencyCode")).build();

                    Category category = new Category.Builder(rs.getString("categoryName"))
                            .id(rs.getInt("categoryID")).build();

                    Car car = new Car.Builder()
                            .carID(rs.getInt("carID"))
                            .brand(rs.getString("brand"))
                            .model(rs.getString("model"))
                            .year(rs.getInt("manufacture_year"))
                            .plate(rs.getString("plate"))
                            .doors(rs.getInt("doors"))
                            .passengers(rs.getInt("passengers"))
                            .description(rs.getString("description"))
                            .mileage(rs.getInt("mileage"))
                            .currentAgency(agency)
                            .category(category).build();

                    result.add(car);
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public boolean addCategory(String categoryName, String description) throws DuplicateEntryException {
        return addCategoryRecord("car_categories", categoryName, description);
    }

    @Override
    public boolean updateCategory(int categoryID, String categoryName, String description) throws DuplicateEntryException {
        return updateCategoryRecord("car_categories", categoryName, description, categoryID);
    }

    @Override
    public boolean deleteCategory(int categoryID) throws MySQLIntegrityConstraintViolationException {
        return deleteRecord("car_categories", "categoryID", categoryID);
    }

    @Override
    public List<Category> findAllCategories() {
        return findCategories("car_categories");
    }

    @Override
    public boolean addCarTariff(int categoryID, BigDecimal dailyPrice, BigDecimal weeklyPrice, BigDecimal kmPrice,
                                BigDecimal freeKmPrice, LocalDate fromDate, LocalDate toDate) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;

        String query = "INSERT INTO car_pricelist (categoryID, dailyPrice, weeklyPrice, kmPrice, freeKmPrice, fromDate, toDate) "
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, categoryID);
            statement.setBigDecimal(2, dailyPrice);
            statement.setBigDecimal(3, weeklyPrice);
            statement.setBigDecimal(4, kmPrice);
            statement.setBigDecimal(5, freeKmPrice);
            statement.setDate(6, Date.valueOf(fromDate));
            statement.setDate(7, Date.valueOf(toDate));

            if (statement.executeUpdate() == 1)
                result = true;

        } catch (MySQLIntegrityConstraintViolationException e) {
            throw new DuplicateEntryException(e);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public boolean updateCarTariff(int tariffID, int categoryID, BigDecimal dailyPrice, BigDecimal weeklyPrice,
                                   BigDecimal kmPrice, BigDecimal freeKmPrice, LocalDate fromDate, LocalDate toDate)
            throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;
        String query = "UPDATE car_pricelist SET categoryID=?, dailyPrice=?, weeklyPrice=?, kmPrice=?, " +
                "freeKmPrice=?, fromDate=?, toDate=? WHERE id=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, categoryID);
            statement.setBigDecimal(2, dailyPrice);
            statement.setBigDecimal(3, weeklyPrice);
            statement.setBigDecimal(4, kmPrice);
            statement.setBigDecimal(5, freeKmPrice);
            statement.setDate(6, Date.valueOf(fromDate));
            statement.setDate(7, Date.valueOf(toDate));
            statement.setInt(8, tariffID);

            if (statement.executeUpdate() == 1)
                result = true;

        } catch (MySQLIntegrityConstraintViolationException e) {
            throw new DuplicateEntryException(e);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public boolean deleteCarTariff(int tariffID) throws MySQLIntegrityConstraintViolationException {
        return deleteRecord("car_pricelist", "id", tariffID);
    }

    @Override
    public List<CarTariff> findCarTariffs() {
        List<CarTariff> result = new ArrayList<>();
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM car_categories LEFT JOIN car_pricelist ON " +
                "car_categories.categoryID = car_pricelist.categoryID ORDER BY categoryName, fromDate";

        try {
            statement = conn.prepareStatement(query);

            if (statement.execute()) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    Category category = new Category.Builder(rs.getString("categoryName"))
                            .id(rs.getInt("categoryID")).build();

                    LocalDate fromDate = MySQLDateUtility.convertToLocalDate(rs.getDate("fromDate"));
                    LocalDate toDate = MySQLDateUtility.convertToLocalDate(rs.getDate("toDate"));

                    CarTariff tariff = new CarTariff.Builder()
                            .tariffID(rs.getInt("id"))
                            .category(category)
                            .dailyPrice(rs.getBigDecimal("dailyPrice"))
                            .weeklyPrice(rs.getBigDecimal("weeklyPrice"))
                            .mileagePrice(rs.getBigDecimal("kmPrice"))
                            .freeMileagePrice(rs.getBigDecimal("freeKmPrice"))
                            .fromDate(fromDate)
                            .toDate(toDate).build();

                    result.add(tariff);
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public List<CarTariff> findCarTariffsByCategory(int categoryID) {
        List<CarTariff> result = new ArrayList<>();
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM car_categories LEFT JOIN car_pricelist " +
                "ON car_categories.categoryID = car_pricelist.categoryID " +
                "WHERE fromDate IS NOT NULL AND car_categories.categoryID = ?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, categoryID);

            if (statement.execute()) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    Category category = new Category.Builder(rs.getString("categoryName"))
                            .id(categoryID).build();

                    LocalDate fromDate = MySQLDateUtility.convertToLocalDate(rs.getDate("fromDate"));
                    LocalDate toDate = MySQLDateUtility.convertToLocalDate(rs.getDate("toDate"));

                    CarTariff tariff = new CarTariff.Builder()
                            .tariffID(rs.getInt("id"))
                            .category(category)
                            .dailyPrice(rs.getBigDecimal("dailyPrice"))
                            .weeklyPrice(rs.getBigDecimal("weeklyPrice"))
                            .mileagePrice(rs.getBigDecimal("kmPrice"))
                            .freeMileagePrice(rs.getBigDecimal("freeKmPrice"))
                            .fromDate(fromDate)
                            .toDate(toDate).build();

                    result.add(tariff);
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }
}
