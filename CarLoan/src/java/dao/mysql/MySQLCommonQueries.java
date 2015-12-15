package dao.mysql;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import entity.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static dao.mysql.MySQLDAOFactory.*;

@SuppressWarnings("LawOfDemeter")
class MySQLCommonQueries {
    private static final Logger log = Logger.getLogger(MySQLCommonQueries.class.getName());

    boolean deleteRecord(String table, String column, int recordID)
            throws MySQLIntegrityConstraintViolationException {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;
        String query = String.format("DELETE FROM %s WHERE %s=?", table, column);

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, recordID);

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

    boolean updateBooleanField(String table, String field, String primaryKey, int rowID, Boolean value) {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;
        String query = String.format("UPDATE %s SET %s=? WHERE %s=?", table, field, primaryKey);

        try {
            statement = conn.prepareStatement(query);
            statement.setBoolean(1, value);
            statement.setInt(2, rowID);

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


    boolean addCategoryRecord(String table, String categoryName, String description)
            throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;

        String query = String.format("INSERT INTO %s (categoryName, categoryDescription) VALUES (?, ?)", table);

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, categoryName);
            statement.setString(2, description);

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

    boolean updateCategoryRecord(String table, String categoryName, String description, int categoryID)
            throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;
        String query = String.format("UPDATE %s SET categoryName=?, categoryDescription=? WHERE categoryID=?", table);

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, categoryName);
            statement.setString(2, description);
            statement.setInt(3, categoryID);

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

    List<Category> findCategories(String table) {
        List<Category> result = new ArrayList<>();
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = String.format("SELECT * FROM %s", table);

        try {
            statement = conn.prepareStatement(query);

            if (statement.execute(query)) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    int categoryID = rs.getInt("categoryID");
                    String categoryName = rs.getString("categoryName");
                    String description = rs.getString("categoryDescription");

                    Category category = new Category.Builder(categoryName)
                            .id(categoryID)
                            .description(description).build();

                    result.add(category);
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
