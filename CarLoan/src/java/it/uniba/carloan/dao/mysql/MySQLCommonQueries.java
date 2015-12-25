package it.uniba.carloan.dao.mysql;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.*;

@SuppressWarnings("LawOfDemeter")
class MySQLCommonQueries {
    private static final Logger log = Logger.getLogger(MySQLCommonQueries.class.getName());

    boolean deleteRecord(String table, String column, int recordID)
            throws MySQLIntegrityConstraintViolationException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement statement = null;
        String query = String.format("DELETE FROM %s WHERE %s=?", table, column);

        try {
            conn = getConnection();
            statement = conn.prepareStatement(query);
            statement.setInt(1, recordID);

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


}
