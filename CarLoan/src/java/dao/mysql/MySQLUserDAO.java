package dao.mysql;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.entity.UserDAO;
import dao.exception.DuplicateEntryException;
import entity.Agency;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static dao.mysql.MySQLDAOFactory.*;


public class MySQLUserDAO extends MySQLCommonQueries implements UserDAO {
    private static final Logger log = Logger.getLogger(MySQLUserDAO.class.getName());

    @Override
    public boolean addUser(String name, String surname, String username, String password, String accountType,
                           boolean accessGranted, int agencyID) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;
        String query = "INSERT INTO users (name, surname, username, password, accountType, accessGranted, agencyID)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.setString(5, accountType);
            statement.setBoolean(6, accessGranted);
            statement.setInt(7, agencyID);

            if (statement.executeUpdate() == 1) {
                result = true;
            }

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
    public boolean updateUser(int userID, String name, String surname, String username, String accountType, int agencyID) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;
        String query = "UPDATE users SET name=?, surname=?, username=?, accountType=?, agencyID=? WHERE userID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, username);
            statement.setString(4, accountType);
            statement.setInt(5, agencyID);
            statement.setInt(6, userID);

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
    public boolean deleteUser(int userID) throws MySQLIntegrityConstraintViolationException {
        return deleteRecord("users", "userID", userID);
    }

    @Override
    public boolean updateUserPassword(int userID, String password) {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;
        String query = "UPDATE users SET password=? WHERE userID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, password);
            statement.setInt(2, userID);

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
    public boolean updateAccess(int userID, boolean accessGranted) {
        boolean result = false;
        Connection conn = connect();
        String query = "UPDATE users SET accessGranted=? WHERE userID=?";
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(query);
            statement.setBoolean(1, accessGranted);
            statement.setInt(2, userID);

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

    @SuppressWarnings("LawOfDemeter")
    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM users, agencies WHERE users.agencyID = agencies.agencyID";

        try {
            statement = conn.prepareStatement(query);

            if (statement.execute(query)) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    Agency agency = new Agency.Builder()
                            .id(rs.getInt("agencyID"))
                            .agencyCode(rs.getString("agencyCode"))
                            .city(rs.getString("city"))
                            .address(rs.getString("address"))
                            .phoneNumber(rs.getString("tel"))
                            .faxNumber(rs.getString("fax"))
                            .email(rs.getString("email")).build();

                    User user = new User.Builder()
                            .id(rs.getInt("userID"))
                            .name(rs.getString("name"))
                            .surname(rs.getString("surname"))
                            .username(rs.getString("username"))
                            .password(rs.getString("password"))
                            .accountType(rs.getString("accountType"))
                            .agency(agency)
                            .accessGranted(rs.getBoolean("accessGranted")).build();

                    result.add(user);
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
