package dao.entity;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import entity.Category;
import entity.Penalty;

import java.util.ArrayList;

public interface PenaltyDAO {
    boolean addPenalty(int categoryID, String title, String description)
            throws DuplicateEntryException;
    boolean updatePenalty(int penaltyID, int categoryID, String title, String description)
            throws DuplicateEntryException;
    boolean deletePenalty(int penaltyID) throws MySQLIntegrityConstraintViolationException;
    boolean changePenaltyStatus(int penaltyID, boolean available);
    ArrayList<Penalty> getAllPenalties();
    boolean addCategory(String categoryName, String description) throws DuplicateEntryException;
    boolean updateCategory(int categoryID, String categoryName, String description) throws DuplicateEntryException;
    boolean deleteCategory(int categoryID) throws MySQLIntegrityConstraintViolationException;
    ArrayList<Category> getAllCategories();
}
