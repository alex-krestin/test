package dao.entity;

import dao.exception.DuplicateEntryException;
import entity.User;

import java.util.ArrayList;


public interface UserDAO {
    boolean addUser(String name, String surname, String username, String password, String accountType, boolean accessGranted, int agencyID) throws DuplicateEntryException;
    boolean updateUser(int userID, String name, String surname, String username, String accountType, int agencyID) throws DuplicateEntryException;
    boolean deleteUser(int userID);
    boolean changeUserPassword(int userID, String password);
    boolean changeAccess(int userID, boolean accessGranted);
    ArrayList<User> getAllUsers();
    boolean isExists(String username);
}
