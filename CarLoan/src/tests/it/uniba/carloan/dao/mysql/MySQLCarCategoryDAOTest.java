package it.uniba.carloan.dao.mysql;

import it.uniba.carloan.dao.mysql.tester.MySQLCategoryDAOTester;
import org.junit.Before;

/**
 * MySQLCarCategoryDAO Tester.
 */
public class MySQLCarCategoryDAOTest extends MySQLCategoryDAOTester<MySQLCarCategoryDAO> {

    @Before
    public void before() throws Exception {
        initConnection();
        dao = new MySQLCarCategoryDAO();
    }

} 
