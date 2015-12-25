package it.uniba.carloan.dao.mysql;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import it.uniba.carloan.dao.entity.ContractAdditionDAO;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.exception.UnknownTableException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.*;


public class MySQLContractAdditionDAO extends MySQLCommonQueries implements ContractAdditionDAO {
    private static final Logger log = Logger.getLogger(MySQLContractAdditionDAO.class.getName());

    @Override
    public boolean addSimpleAddition(int additionType, int referenceID, int contractID, BigDecimal price) throws UnknownTableException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement statement = null;
        String table;

        switch (additionType) {
            case 2:
                table = "service_addition";
                break;
            case 3:
                table = "penalty_addition";
                break;
            default:
                throw new UnknownTableException();
        }

        String query = "INSERT INTO " + table + "(referenceID, contractID, price) "
                + "VALUES (?, ?, ?)";

        try {
            conn = getConnection();
            statement = conn.prepareStatement(query);
            statement.setInt(1, referenceID);
            statement.setInt(2, contractID);
            statement.setBigDecimal(3, price);

            if (statement.executeUpdate() == 1)
                result = true;

        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public boolean addBookableAddition(int additionType, int referenceID, int contractID, Date fromDate, Date toDate, BigDecimal price) throws UnknownTableException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement statement = null;
        String table;

        switch (additionType) {
            case 0:
                table = "car_booking";
                break;
            case 1:
                table = "accessory_booking";
                break;
            default:
                throw new UnknownTableException();
        }

        String query = "INSERT INTO " + table + "(referenceID, contractID, fromDate, toDate, price) "
                + "VALUES (?, ?, ?, ?, ?)";

        // Convert java.util.Date to java.sql.Timestamp
        Object sqlFromDate = new java.sql.Timestamp(fromDate.getTime());
        Object sqlToDate = new java.sql.Timestamp(toDate.getTime());

        try {
            conn = getConnection();
            statement = conn.prepareStatement(query);
            statement.setInt(1, referenceID);
            statement.setInt(2, contractID);
            statement.setObject(3, sqlFromDate);
            statement.setObject(4, sqlToDate);
            statement.setBigDecimal(5, price);

            if (statement.executeUpdate() == 1)
                result = true;

        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public boolean deleteAddition(int additionType, int id) throws UnknownTableException, MySQLIntegrityConstraintViolationException {
        String table;

        switch (additionType) {
            case 0:
                table = "car_booking";
                break;
            case 1:
                table = "accessory_booking";
                break;
            case 2:
                table = "service_addition";
                break;
            case 3:
                table = "penalty_addition";
                break;
            default:
                throw new UnknownTableException();
        }

        return deleteRecord(table, "id", id);
    }
}
