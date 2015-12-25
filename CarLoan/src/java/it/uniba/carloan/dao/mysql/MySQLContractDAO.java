package it.uniba.carloan.dao.mysql;

import it.uniba.carloan.dao.entity.ContractDAO;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.mysql.utility.MySOLDateConverter;
import it.uniba.carloan.entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.*;


@SuppressWarnings("LawOfDemeter")
public class MySQLContractDAO implements ContractDAO {
    private static final Logger log = Logger.getLogger(MySQLContractDAO.class.getName());

    @Override
    public List<ContractObject> findAvailableCarsByAgency(int agencyID, int categoryID, LocalDateTime fromDate, LocalDateTime toDate) {
        List<ContractObject> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM cars INNER JOIN car_categories ON cars.categoryID = car_categories.categoryID " +
                "INNER JOIN car_pricelist ON car_categories.categoryID = car_pricelist.categoryID " +
                "LEFT JOIN car_booking ON cars.carID = car_booking.referenceID WHERE cars.currentAgencyID = ? " +
                "AND cars.categoryID = ? AND cars.available IS TRUE AND (car_pricelist.fromDate <= ? AND " +
                "(car_pricelist.toDate IS NULL OR car_pricelist.toDate >= ?)) AND " +
                "((car_booking.toDate IS NULL AND car_booking.fromDate IS NULL) OR (? >= car_booking.toDate OR " +
                "? <= car_booking.fromDate))";

        try {
            conn = getConnection();
            // convert LocalDateTime to java.sql.Date
            Date sqlFromDate = MySOLDateConverter.convertToSqlDate(fromDate);

            // convert LocalDateTime to java.sql.Timestamp
            Timestamp fromDateTs = Timestamp.valueOf(fromDate);
            Timestamp toDateTs = Timestamp.valueOf(toDate);

            statement = conn.prepareStatement(query);
            statement.setInt(1, agencyID);
            statement.setInt(2, categoryID);

            statement.setDate(3, sqlFromDate);
            statement.setDate(4, sqlFromDate);
            statement.setObject(5, fromDateTs);
            statement.setObject(6, toDateTs);

            if (statement.execute()) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    Category category = new Category.Builder(rs.getString("categoryName"))
                            .id(rs.getInt("categoryID")).build();

                    Agency agency = new Agency.Builder().id(agencyID).build();

                    Car car = new Car.Builder()
                            .carID(rs.getInt("carID"))
                            .brand(rs.getString("brand"))
                            .model(rs.getString("model"))
                            .year(rs.getInt("manufacture_year"))
                            .plate(rs.getString("plate"))
                            .doors(rs.getInt("doors"))
                            .passengers(rs.getInt("passengers"))
                            .mileage(rs.getInt("mileage"))
                            .currentAgency(agency)
                            .category(category).build();

                    CarTariff tariff = new CarTariff.Builder()
                            .dailyPrice(rs.getBigDecimal("dailyPrice"))
                            .weeklyPrice(rs.getBigDecimal("weeklyPrice"))
                            .mileagePrice(rs.getBigDecimal("kmPrice"))
                            .freeMileagePrice(rs.getBigDecimal("freeKmPrice")).build();

                    ContractObject contractObject = new ContractObject<>(car, tariff);

                    result.add(contractObject);
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }


    @Override
    public List<ContractObject> findAvailableAccessoriesByAgencyAndCategory(int agencyID, int categoryID, LocalDateTime fromDate, LocalDateTime toDate) {
        List<ContractObject> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM accessories INNER JOIN accessory_categories ON " +
                "accessories.categoryID = accessory_categories.categoryID INNER JOIN accessory_pricelist ON " +
                "accessory_categories.categoryID = accessory_pricelist.categoryID LEFT JOIN accessory_booking " +
                "ON accessories.accessoryID = accessory_booking.referenceID WHERE currentAgencyID = ? AND " +
                "accessories.categoryID = ? AND " +
                "accessories.available IS TRUE AND (accessory_pricelist.toDate IS NULL " +
                "OR accessory_pricelist.toDate >=?) AND ((accessory_booking.toDate IS NULL " +
                "AND accessory_booking.fromDate IS NULL) OR (? >= accessory_booking.toDate OR " +
                "(? >= accessory_booking.toDate OR ? <= accessory_booking.fromDate))) " +
                "ORDER BY accessories.accessoryID, accessory_pricelist.fromDate DESC";

        try {
            conn = getConnection();
            // convert LocalDateTime to java.sql.Date
            Date sqlFromDate = MySOLDateConverter.convertToSqlDate(fromDate);

            // convert LocalDateTime to java.sql.Timestamp
            Timestamp fromDateTs = Timestamp.valueOf(fromDate);
            Timestamp toDateTs = Timestamp.valueOf(toDate);

            statement = conn.prepareStatement(query);
            statement.setInt(1, agencyID);
            statement.setInt(2, categoryID);

            statement.setDate(3, sqlFromDate);
            statement.setDate(4, sqlFromDate);
            statement.setObject(5, fromDateTs);
            statement.setObject(6, toDateTs);

            if (statement.execute()) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                int currentAccessoryID = 0;

                while (rs.next()) {
                    int accessoryID = rs.getInt("accessoryID");

                    if (accessoryID != currentAccessoryID) {

                        Category category = new Category.Builder(rs.getString("categoryName"))
                                .id(categoryID).build();

                        Accessory accessory = new Accessory.Builder()
                                .id(accessoryID)
                                .title(rs.getString("title"))
                                .inventoryCode(rs.getString("inventoryCode"))
                                .category(category).build();

                        AccessoryTariff tariff = new AccessoryTariff.Builder()
                                .dailyPrice(rs.getBigDecimal("dailyPrice"))
                                .maxDays(rs.getInt("maxDays")).build();

                        ContractObject contractObject = new ContractObject<>(accessory, tariff);

                        result.add(contractObject);
                        currentAccessoryID = accessoryID;
                    }
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public List<ContractObject> findAvailableServices(LocalDate date) {
        List<ContractObject> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM services INNER JOIN service_pricelist ON " +
                "services.serviceID = service_pricelist.serviceID " +
                "WHERE services.available IS TRUE AND service_pricelist.fromDate <= ? " +
                "AND (service_pricelist.toDate IS NULL OR service_pricelist.toDate >=?) " +
                "ORDER BY services.serviceID, service_pricelist.fromDate DESC"; //newest tariff is higher

        try {
            conn = getConnection();
            // convert LocalDate to java.sql.Date
            Date sqlDate = Date.valueOf(date);

            statement = conn.prepareStatement(query);
            statement.setDate(1, sqlDate);
            statement.setDate(2, sqlDate);

            if (statement.execute()) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                int currentServiceID = 0;

                while (rs.next()) {
                    int serviceID = rs.getInt("serviceID");

                    if (serviceID != currentServiceID) {
                        Service service = new Service.Builder()
                                .id(serviceID)
                                .title(rs.getString("title")).build();

                        ServiceTariff tariff = new ServiceTariff.Builder()
                                .price(rs.getBigDecimal("price")).build();

                        ContractObject<Service> contractObject = new ContractObject<>(service, tariff);

                        result.add(contractObject);
                        currentServiceID = serviceID;
                    }
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }
}
