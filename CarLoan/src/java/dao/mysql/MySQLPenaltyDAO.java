package dao.mysql;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.entity.PenaltyDAO;
import dao.exception.DuplicateEntryException;
import entity.Category;
import entity.Penalty;
import entity.PenaltyTariff;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static dao.mysql.MySQLDAOFactory.*;


@SuppressWarnings("LawOfDemeter")
public class MySQLPenaltyDAO extends MySQLCommonQueries implements PenaltyDAO {
    private static final Logger log = Logger.getLogger(MySQLPenaltyDAO.class.getName());

    @Override
    public boolean addPenalty(int categoryID, String title, String description) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;

        String query = "INSERT INTO penalties (categoryID, title, description) "
                + " VALUES (?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, categoryID);
            statement.setString(2, title);
            statement.setString(3, description);

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
    public boolean updatePenalty(int penaltyID, int categoryID, String title, String description) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;
        String query = "UPDATE penalties SET categoryID=?, title=?, description=? WHERE penaltyID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, categoryID);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.setInt(4, penaltyID);

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
    public boolean deletePenalty(int penaltyID) throws MySQLIntegrityConstraintViolationException {
        return deleteRecord("penalties", "penaltyID", penaltyID);
    }

    @Override
    public boolean updatePenaltyStatus(int penaltyID, boolean available) {
        return updateBooleanField("penalties", "available", "penaltyID", penaltyID, available);
    }

    @Override
    public List<Penalty> findAllPenalties() {
        List<Penalty> result = new ArrayList<>();
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM penalties, penalty_categories WHERE penalties.categoryID = " +
                "penalty_categories.categoryID AND available = TRUE";

        try {
            statement = conn.prepareStatement(query);

            if (statement.execute()) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    Category category = new Category.Builder(rs.getString("categoryName"))
                            .id(rs.getInt("categoryID")).build();

                    Penalty penalty = new Penalty.Builder()
                            .id(rs.getInt("penaltyID"))
                            .title(rs.getString("title"))
                            .description(rs.getString("description"))
                            .category(category).build();

                    result.add(penalty);
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
        return addCategoryRecord("penalty_categories", categoryName, description);
    }

    @Override
    public boolean updateCategory(int categoryID, String categoryName, String description) throws DuplicateEntryException {
        return updateCategoryRecord("penalty_categories", categoryName, description, categoryID);
    }

    @Override
    public boolean deleteCategory(int categoryID) throws MySQLIntegrityConstraintViolationException {
        return deleteRecord("penalty_categories", "categoryID", categoryID);
    }

    @Override
    public List<Category> findAllCategories() {
        return findCategories("penalty_categories");
    }

    @Override
    public boolean addPenaltyTariff(int penaltyID, BigDecimal fixedPrice, LocalDate fromDate, LocalDate toDate) {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;

        String query = "INSERT INTO penalty_pricelist (penaltyID, price, fromDate, toDate) VALUES (?, ?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, penaltyID);
            statement.setBigDecimal(2, fixedPrice);
            statement.setDate(3, Date.valueOf(fromDate));
            statement.setDate(4, Date.valueOf(toDate));

            if (statement.executeUpdate() == 1)
                result = true;

        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public boolean updatePenaltyTariff(int tariffID, int penaltyID, BigDecimal fixedPrice, LocalDate fromDate, LocalDate toDate) {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;

        String query = "UPDATE penalty_pricelist SET penaltyID=?, price=?, fromDate=?, toDate=? WHERE id=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, penaltyID);
            statement.setBigDecimal(2, fixedPrice);
            statement.setDate(3, Date.valueOf(fromDate));
            statement.setDate(4, Date.valueOf(toDate));
            statement.setInt(5, tariffID);

            if (statement.executeUpdate() == 1)
                result = true;

        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public boolean deletePenaltyTariff(int tariffID) throws MySQLIntegrityConstraintViolationException {
        return deleteRecord("penalty_pricelist", "id", tariffID);
    }

    @Override
    public List<PenaltyTariff> findPenaltyTariffs() {
        List<PenaltyTariff> result = new ArrayList<>();
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM penalties INNER JOIN penalty_categories ON " +
                "penalties.categoryID = penalty_categories.categoryID LEFT JOIN penalty_pricelist ON " +
                "penalties.penaltyID = penalty_pricelist.penaltyID WHERE available = TRUE ORDER BY title, fromDate";

        try {
            statement = conn.prepareStatement(query);

            if (statement.execute()) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    Category category = new Category.Builder(rs.getString("categoryName"))
                            .id(rs.getInt("categoryID")).build();

                    Penalty penalty = new Penalty.Builder()
                            .id(rs.getInt("penaltyID"))
                            .title(rs.getString("title"))
                            .description(rs.getString("description"))
                            .category(category).build();

                    LocalDate fromDate = MySQLDateUtility.convertToLocalDate(rs.getDate("fromDate"));
                    LocalDate toDate = MySQLDateUtility.convertToLocalDate(rs.getDate("toDate"));

                    PenaltyTariff tariff = new PenaltyTariff.Builder()
                            .tariffID(rs.getInt("id"))
                            .penalty(penalty)
                            .price(rs.getBigDecimal("price"))
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
    public List<PenaltyTariff> findPenaltyTariffsByID(int penaltyID) {
        List<PenaltyTariff> result = new ArrayList<>();
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM penalties INNER JOIN penalty_categories ON " +
                "penalties.categoryID = penalty_categories.categoryID LEFT JOIN penalty_pricelist ON " +
                "penalties.penaltyID = penalty_pricelist.penaltyID WHERE fromDate IS NOT NULL " +
                "AND penalties.penaltyID = ?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, penaltyID);

            if (statement.execute()) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    Category category = new Category.Builder(rs.getString("categoryName"))
                            .id(rs.getInt("categoryID")).build();

                    Penalty penalty = new Penalty.Builder()
                            .id(penaltyID)
                            .title(rs.getString("title"))
                            .description(rs.getString("description"))
                            .category(category).build();

                    LocalDate fromDate = MySQLDateUtility.convertToLocalDate(rs.getDate("fromDate"));
                    LocalDate toDate = MySQLDateUtility.convertToLocalDate(rs.getDate("toDate"));

                    PenaltyTariff tariff = new PenaltyTariff.Builder()
                            .tariffID(rs.getInt("id"))
                            .penalty(penalty)
                            .price(rs.getBigDecimal("price"))
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
