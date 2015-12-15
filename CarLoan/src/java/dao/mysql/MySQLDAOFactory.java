package dao.mysql;

import dao.DAOFactory;
import dao.entity.*;
import utility.DBConfigObject;
import utility.SessionData;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MySQLDAOFactory extends DAOFactory {
    private static final Logger log = Logger.getLogger(MySQLDAOFactory.class.getName());

    private static String SERVER;
    private static int PORT;
    private static String DB;
    private static String USER;
    private static String PASSWORD;

    private static Connection conn = null;

    private static void setConnectionParams(DBConfigObject dbco) {
        SERVER = dbco.getHost();
        PORT = dbco.getPort();
        DB = dbco.getDatabase();
        USER = dbco.getUser();
        PASSWORD = dbco.getPassword();
    }

     static Connection connect() {

        // Get DB configuration
        DBConfigObject dbco = SessionData.getDbco();
        setConnectionParams(dbco);

        // Load JDBC driver
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed load JDBC driver: ", e);
        }

        try {
            conn = DriverManager.getConnection(("jdbc:mysql://"+SERVER+":"+PORT+"/"+DB), USER, PASSWORD);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Connection Error: ", e);
        }

        return conn;
    }

    static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                log.log(Level.WARNING, "Failed close MySQL Statement: ", e);
            }
        }
    }

    static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.log(Level.WARNING, "Failed close MySQL Connection: ", e);
            }
        }
    }

    static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.log(Level.WARNING, "Failed close MySQL ResultSet: ", e);
            }
        }
    }

    @Override
    public UserDAO getUserDAO() throws IllegalAccessException, InstantiationException {
        return (UserDAO) newDao(MySQLUserDAO.class);
    }

    @Override
    public ServiceDAO getServiceDAO() throws IllegalAccessException, InstantiationException {
        return (ServiceDAO) newDao(MySQLServiceDAO.class);
    }

    @Override
    public AccessoryDAO getAccessoryDAO() throws IllegalAccessException, InstantiationException {
        return (AccessoryDAO) newDao(MySQLAccessoryDAO.class);
    }

    @Override
    public PenaltyDAO getPenaltyDAO() throws InstantiationException, IllegalAccessException {
        return (PenaltyDAO) newDao(MySQLPenaltyDAO.class);
    }

    @Override
    public CarDAO getCarDAO() throws IllegalAccessException, InstantiationException {
        return (CarDAO) newDao(MySQLCarDAO.class);
    }

    @Override
    public ClientDAO getClientDAO() throws InstantiationException, IllegalAccessException {
        return (ClientDAO) newDao(MySQLClientDAO.class);
    }

    @Override
    public ContractDAO getContractDAO() throws InstantiationException, IllegalAccessException {
        return (ContractDAO) newDao(MySQLContractDAO.class);
    }

    @Override
    public SystemDAO getSystemDAO() throws IllegalAccessException, InstantiationException {
        return (SystemDAO) newDao(MySQLSystemDAO.class);
    }

    @Override
    public AgencyDAO getAgencyDAO() throws IllegalAccessException, InstantiationException {
        return (AgencyDAO) newDao(MySQLAgencyDAO.class);
    }
}
