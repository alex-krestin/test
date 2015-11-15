package dao.mysql;

import entity.User;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

public class MySQL_UserDAOTest extends TestCase {

    private static User user = new User();
    private static User user2 = new User();
    private MySQLUserDAO test = new MySQLUserDAO();

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {

    }

    public void testAddUser() throws Exception {
    //    boolean res1 = test.addUser(user2);
    //    assertEquals(true, res1);
    }

    public void testUpdateUser() throws Exception {

    }

    public void testDeleteUser() throws Exception {

    }

    public void testChangeUserPassword() throws Exception {

    }

    public void testChangeUserAccountType() throws Exception {

    }

    public void testGrantAccess() throws Exception {

    }

    public void testDenyAccess() throws Exception {

    }

    public void testGetUser() throws Exception {

    }

    public void testGetAllUsers() throws Exception {

    }

    public void testGetAllUsers1() throws Exception {

    }

    public void testIsExists() throws Exception {
    //    boolean res = test.isExists(user);
    //    assertEquals(true, res);
    }

    public void testIsExists2() throws Exception {
    //    boolean res = test.isExists(user2);
    //    assertEquals(false, res);
    }
}