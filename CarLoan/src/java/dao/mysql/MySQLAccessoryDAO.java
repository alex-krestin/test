package dao.mysql;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.entity.AccessoryDAO;
import dao.exception.DuplicateEntryException;
import entity.Accessory;
import entity.AccessoryTariff;
import entity.Agency;
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
public class MySQLAccessoryDAO extends MySQLCommonQueries implements AccessoryDAO {
    private static final Logger log = Logger.getLogger(MySQLAccessoryDAO.class.getName());

    @Override
    public boolean addAccessory(int categoryID, String title, String description, String inventoryCode, int currentAgencyID)
            throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;
        String query = "INSERT INTO accessories (categoryID, title, description, inventoryCode, currentAgencyID) "
                + " VALUES (?, ?, ?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, categoryID);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.setString(4, inventoryCode);
            statement.setInt(5, currentAgencyID);

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
    public boolean updateAccessory(int accessoryID, int categoryID, String title, String description,
                                   String inventoryCode) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;
        String query = "UPDATE accessories SET categoryID=?, title=?, description=?, inventoryCode=? WHERE accessoryID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, categoryID);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.setString(4, inventoryCode);
            statement.setInt(5, accessoryID);

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
    public boolean deleteAccessory(int accessoryID) throws MySQLIntegrityConstraintViolationException {
        return deleteRecord("accessories", "accessoryID", accessoryID);
    }

    @Override
    public boolean updateAccessoryStatus(int accessoryID, boolean available) {
        return updateBooleanField("accessories", "available", "accessoryID", accessoryID, available);
    }

    @SuppressWarnings("LawOfDemeter")
    @Override
    public List<Accessory> findAllAccessories() {
        List<Accessory> result = new ArrayList<>();
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM accessories, accessory_categories, agencies WHERE accessories.categoryID = " +
                "accessory_categories.categoryID AND accessories.currentAgencyID = agencies.agencyID" +
                " AND available = TRUE";

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
                            .id(rs.getInt("agencyID"))
                            .agencyCode(rs.getString("agencyCode"))
                            .city(rs.getString("city"))
                            .address(rs.getString("address"))
                            .phoneNumber(rs.getString("tel"))
                            .faxNumber(rs.getString("fax"))
                            .email(rs.getString("email")).build();

                    Accessory accessory = new Accessory.Builder()
                            .id(rs.getInt("accessoryID"))
                            .title(rs.getString("title"))
                            .description(rs.getString("description"))
                            .inventoryCode(rs.getString("inventoryCode"))
                            .category(category)
                            .currentAgency(agency).build();

                    result.add(accessory);
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
        return addCategoryRecord("accessory_categories", categoryName, description);
    }

    @Override
    public boolean updateCategory(int categoryID, String categoryName, String description)
            throws DuplicateEntryException {

        return updateCategoryRecord("accessory_categories", categoryName, description, categoryID);
    }

    @Override
    public boolean deleteCategory(int categoryID) throws MySQLIntegrityConstraintViolationException {
        return deleteRecord("accessory_categories", "categoryID", categoryID);
    }

    @Override
    public List<Category> findAllCategories() {
        return findCategories("accessory_categories");
    }



    @Override
    public boolean addAccessoryTariff(int categoryID, BigDecimal dailyPrice, int maxDays, LocalDate fromDate, LocalDate toDate) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();

        String query = "INSERT INTO accessory_pricelist (categoryID, dailyPrice, maxDays, fromDate, toDate) "
                + " VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, categoryID);
            statement.setBigDecimal(2, dailyPrice);
            statement.setInt(3, maxDays);
            statement.setDate(4, Date.valueOf(fromDate));
            statement.setDate(5, Date.valueOf(toDate));

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
    public boolean updateAccessoryTariff(int tariffID, int categoryID, BigDecimal dailyPrice, int maxDays, LocalDate fromDate, LocalDate toDate) throws DuplicateEntryException {
        Connection conn = connect();
        String query = "UPDATE accessory_pricelist SET categoryID=?, dailyPrice=?, maxDays=?, fromDate=?, toDate=? WHERE id=?";

        boolean result = false;
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, categoryID);
            statement.setBigDecimal(2, dailyPrice);
            statement.setInt(3, maxDays);
            statement.setDate(4, Date.valueOf(fromDate));
            statement.setDate(5, Date.valueOf(toDate));
            statement.setInt(6, tariffID);

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
    public boolean deleteAccessoryTariff(int tariffID) throws MySQLIntegrityConstraintViolationException {
        return deleteRecord("accessory_pricelist", "id", tariffID);
    }

    @Override
    public List<AccessoryTariff> findAccessoryTariffs() {
        List<AccessoryTariff> result = new ArrayList<>();
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM accessory_categories LEFT JOIN accessory_pricelist ON " +
                "accessory_categories.categoryID = accessory_pricelist.categoryID ORDER BY categoryName, fromDate";

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

                    AccessoryTariff tariff = new AccessoryTariff.Builder()
                            .tariffID(rs.getInt("id"))
                            .dailyPrice(rs.getBigDecimal("dailyPrice"))
                            .maxDays(rs.getInt("maxDays"))
                            .category(category)
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
    public List<AccessoryTariff> findAccessoryTariffsByCategory(int categoryID) {
        List<AccessoryTariff> result = new ArrayList<>();
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM accessory_categories LEFT JOIN accessory_pricelist " +
                "ON accessory_categories.categoryID = accessory_pricelist.categoryID " +
                "WHERE fromDate IS NOT NULL AND accessory_categories.categoryID = ?";

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

                    AccessoryTariff tariff = new AccessoryTariff.Builder()
                            .tariffID(rs.getInt("id"))
                            .dailyPrice(rs.getBigDecimal("dailyPrice"))
                            .maxDays(rs.getInt("maxDays"))
                            .category(category)
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
