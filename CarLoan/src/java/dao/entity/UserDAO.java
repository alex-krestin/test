package dao.entity;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import entity.User;

import java.util.List;


public interface UserDAO {
    boolean addUser(String name, String surname, String username, String password, String accountType, boolean accessGranted, int agencyID) throws DuplicateEntryException;
    boolean updateUser(int userID, String name, String surname, String username, String accountType, int agencyID) throws DuplicateEntryException;
    boolean deleteUser(int userID) throws MySQLIntegrityConstraintViolationException;
    boolean updateUserPassword(int userID, String password);
    boolean updateAccess(int userID, boolean accessGranted);
    List<User> getAllUsers();
}
