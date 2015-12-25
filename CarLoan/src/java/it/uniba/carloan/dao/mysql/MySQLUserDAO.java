package it.uniba.carloan.dao.mysql;


import it.uniba.carloan.dao.entity.UserDAO;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Agency;
import it.uniba.carloan.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLUserDAO extends MySQLAbstractDAO<User, Integer> implements UserDAO {
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO users (name, surname, username, password, accountType, accessGranted, agencyID)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE users SET name=?, surname=?, username=?, accountType=?, agencyID=? WHERE userID=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM users WHERE userID=?";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM users, agencies WHERE users.agencyID = agencies.agencyID";
    }

    @Override
    protected String getFindByPKQuery() {
        return "SELECT * FROM users, agencies WHERE users.agencyID = agencies.agencyID AND userID=?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws PersistenceException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getSurname());
            statement.setString(3, object.getUsername());
            statement.setString(4, object.getPassword());
            statement.setString(5, object.getAccountType());
            statement.setBoolean(6, object.isAccessGranted());
            statement.setInt(7, object.getAgencyID());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws PersistenceException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getSurname());
            statement.setString(3, object.getUsername());
            statement.setString(4, object.getAccountType());
            statement.setInt(5, object.getAgencyID());
            statement.setInt(6, object.getId());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws PersistenceException {
        List<User> result = new ArrayList<>();
        if (rs != null) {
            try {
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
            } catch (SQLException e) {
                throw new PersistenceException(e);
            }
        }

        return result;
    }

    @Override
    public boolean updatePassword(User object) throws PersistenceException {
        String query = "UPDATE users SET password=? WHERE userID=?";
        return updateGenericField(query, object.getPassword(), object.getId());
    }

    @Override
    public boolean updateAccess(User object) throws PersistenceException {
        String query = "UPDATE users SET accessGranted=? WHERE userID=?";
        return updateGenericField(query, object.isAccessGranted(), object.getId());
    }
}
