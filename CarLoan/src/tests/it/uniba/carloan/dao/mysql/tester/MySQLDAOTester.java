package it.uniba.carloan.dao.mysql.tester;


import it.uniba.carloan.dao.config.MySQLDatabaseConfig;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLDAOTester {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private final MySQLDatabaseConfig config = new MySQLDatabaseConfig("localhost", "carloan", "java", "pass123");
    protected Connection conn;

    @After
    public void tearDown() throws Exception {
        conn.rollback();
    }

    protected void initConnection() {
        MySQLDatabaseConnector databaseConnector = new MySQLDatabaseConnector();
        databaseConnector.setDataSource(config);
        try {
            conn = MySQLDatabaseConnector.getConnection();
            conn.setAutoCommit(false);
        } catch (PersistenceException | SQLException e) {
            e.printStackTrace();
        }
    }


}
