package it.uniba.carloan.dao.mysql;

import it.uniba.carloan.business.security.DatabaseConfigObject;
import it.uniba.carloan.dao.entity.SystemDAO;
import it.uniba.carloan.dao.exception.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.*;


public class MySQLSystemDAO implements SystemDAO {
    @Override
    public boolean checkConnection(DatabaseConfigObject object) throws PersistenceException {

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = getConnection(object);
            statement = conn.prepareStatement("show tables");
            return statement.execute();

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(conn);
        }
    }
}
