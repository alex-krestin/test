package it.uniba.carloan.dao.mysql;

import it.uniba.carloan.dao.mysql.tester.MySQLCategoryDAOTester;
import org.junit.Before;

/** 
* MySQLPenaltyCategoryDAO Tester.
*/ 
public class MySQLPenaltyCategoryDAOTest extends MySQLCategoryDAOTester<MySQLPenaltyCategoryDAO> {

    @Before
    public void before() throws Exception {
        initConnection();
        dao = new MySQLPenaltyCategoryDAO();
    }
} 
