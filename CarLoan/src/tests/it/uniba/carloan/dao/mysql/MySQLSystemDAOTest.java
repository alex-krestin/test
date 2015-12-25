package it.uniba.carloan.dao.mysql;

import it.uniba.carloan.dao.config.MySQLDatabaseConfig;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.sql.SQLException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* MySQLSystemDAO Tester.
*/
public class MySQLSystemDAOTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {

    }

    /**
    * Method: checkConnection(DatabaseConfigObject object)
    */
    @Test
    public void testCheckConnection() throws Exception {
        MySQLSystemDAO dao = new MySQLSystemDAO();

        // Test with correct parameters
        MySQLDatabaseConfig config = new MySQLDatabaseConfig("localhost", "carloan", "java", "pass123");
        Assert.assertTrue(dao.checkConnection(config));

        // Test with wrong parameters
        config = new MySQLDatabaseConfig("host", "database", "user", "password");

        // Prevent logs from processed by default Console handler.
        Logger.getLogger("").setLevel(Level.OFF);

        exception.expect(PersistenceException.class);
        dao.checkConnection(config);
    }

} 
