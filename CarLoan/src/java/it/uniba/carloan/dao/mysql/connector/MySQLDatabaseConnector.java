package it.uniba.carloan.dao.mysql.connector;


import it.uniba.carloan.business.security.DatabaseConfigObject;
import it.uniba.carloan.dao.DatabaseConnector;
import it.uniba.carloan.dao.config.MySQLDatabaseConfig;
import it.uniba.carloan.dao.exception.PersistenceException;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLDatabaseConnector extends DatabaseConnector {
    private static final Logger log = Logger.getLogger(MySQLDatabaseConnector.class.getName());

    private static Connection activeConnection = null;

    private static BasicDataSource getDataSource(DatabaseConfigObject object) {
        MySQLDatabaseConfig config = (MySQLDatabaseConfig) object;
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://"+config.getHost()+":"+config.getPort()+"/"+config.getDatabase());
        dataSource.setUsername(config.getUser());
        dataSource.setPassword(config.getPassword());

        return dataSource;
    }

    @Override
    public void setDataSource(DatabaseConfigObject object) {
        dataSource = getDataSource(object);
    }

    public static Connection getConnection() throws PersistenceException {
        activeConnection = null;

        try {
            activeConnection = dataSource.getConnection();
        } catch (SQLException e) {
            log.log(Level.WARNING, "Failed connect to MySQL: ", e);
            throw new PersistenceException(e);
        }
        return activeConnection;
    }

    public static Connection getConnection(DatabaseConfigObject object) throws PersistenceException {
        BasicDataSource dataSource = getDataSource(object);
        try {
            activeConnection = dataSource.getConnection();
        } catch (SQLException e) {
            log.log(Level.WARNING, "Failed connect to MySQL: ", e);
            throw new PersistenceException(e);
        }
        return activeConnection;
    }

    public static Connection getActiveConnection() {
        return activeConnection;
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                log.log(Level.WARNING, "Failed close MySQL Statement: ", e);
            }
        }
    }


    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.log(Level.WARNING, "Failed close MySQL Connection: ", e);
            }
        }
    }

    public static void closeConnection() {
        closeConnection(activeConnection);
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.log(Level.WARNING, "Failed close MySQL ResultSet: ", e);
            }
        }
    }
}
