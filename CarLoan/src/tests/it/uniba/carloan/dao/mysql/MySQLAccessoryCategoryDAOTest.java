package it.uniba.carloan.dao.mysql;

import it.uniba.carloan.dao.mysql.tester.MySQLCategoryDAOTester;
import org.junit.Before;

/**
 * MySQLAccessoryCategoryDAO Tester.
 */
public class MySQLAccessoryCategoryDAOTest extends MySQLCategoryDAOTester<MySQLAccessoryCategoryDAO> {

    @Before
    public void before() throws Exception {
        initConnection();
        dao = new MySQLAccessoryCategoryDAO();
    }

} 
