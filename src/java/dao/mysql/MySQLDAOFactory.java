package dao.mysql;

import dao.entity.*;
import dao.DAOFactory;
import utility.SessionData;
import utility.DBConfigObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class MySQLDAOFactory extends DAOFactory {

    private static String SERVER;
    private static int PORT;
    private static String DB;
    private static String USER;
    private static String PASSWORD;

    private static Connection conn = null;

    public MySQLDAOFactory() {
    }

    private static void setConnectionParams(DBConfigObject dbco) {
        SERVER = dbco.getHost();
        PORT = dbco.getPort();
        DB = dbco.getDatabase();
        USER = dbco.getUser();
        PASSWORD = dbco.getPassword();
    }

    public static Connection connect() {

        // Get DB configuration
        DBConfigObject dbco = SessionData.getDbco();
        setConnectionParams(dbco);

        // Load JDBC driver
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(("jdbc:mysql://"+SERVER+":"+PORT+"/"+DB), USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
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
    public SystemDAO getSystemDAO() throws IllegalAccessException, InstantiationException {
        return (SystemDAO) newDao(MySQLSystemDAO.class);
    }

    @Override
    public AgencyDAO getAgencyDAO() throws IllegalAccessException, InstantiationException {
        return (AgencyDAO) newDao(MySQLAgencyDAO.class);
    }
}
