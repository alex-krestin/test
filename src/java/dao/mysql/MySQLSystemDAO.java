package dao.mysql;

import dao.entity.SystemDAO;
import utility.DBConfigObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class MySQLSystemDAO implements SystemDAO {

    @Override
    public Boolean checkConnection(DBConfigObject config) {
        String SERVER = config.getHost();
        int PORT = config.getPort();
        String DB = config.getDatabase();
        String USER = config.getUser();
        String PASSWORD = config.getPassword();
        boolean result = false;

        try {
            // Load JDBC driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            // Create connection
            Connection conn = DriverManager.getConnection(("jdbc:mysql://" + SERVER + ":" + PORT + "/" + DB), USER, PASSWORD);

            Statement stmt = conn.createStatement();

            //Execute a simple command; We do not trap the response
            //We are only wanting a connection test

            if(stmt.execute("show tables;"))
            {
                result = true;
            }

            conn.close();
            stmt.close();
        } catch (Exception e) {
            // do nothing
        }

        return result;
    }
}
