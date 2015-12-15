package dao.mysql;

import dao.entity.SystemDAO;
import utility.DBConfigObject;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MySQLSystemDAO implements SystemDAO {
    private static final Logger log = Logger.getLogger(MySQLSystemDAO.class.getName());


    //TODO REWRITE IT!!!

    @Override
    public Boolean checkConnection(DBConfigObject config) {
        String SERVER = config.getHost();
        int PORT = config.getPort();
        String DB = config.getDatabase();
        String USER = config.getUser();
        String PASSWORD = config.getPassword();
        boolean result = false;

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // Load JDBC driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            // Create connection
            conn = DriverManager.getConnection(("jdbc:mysql://" + SERVER + ":" + PORT + "/" + DB), USER, PASSWORD);
            statement = conn.prepareStatement("show tables");

            if (statement.executeUpdate() == 1) {
                result = true;
            }

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            log.log(Level.SEVERE, "Failed load JDBC driver: ", e);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        }

        MySQLDAOFactory.closeStatement(statement);
        MySQLDAOFactory.closeConnection(conn);

        return result;
    }
}
