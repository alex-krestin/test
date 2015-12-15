package dao.entity;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import entity.Category;
import entity.Penalty;
import entity.PenaltyTariff;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PenaltyDAO {
    boolean addPenalty(int categoryID, String title, String description)
            throws DuplicateEntryException;
    boolean updatePenalty(int penaltyID, int categoryID, String title, String description)
            throws DuplicateEntryException;
    boolean deletePenalty(int penaltyID) throws MySQLIntegrityConstraintViolationException;
    boolean updatePenaltyStatus(int penaltyID, boolean available);
    List<Penalty> findAllPenalties();
    boolean addCategory(String categoryName, String description) throws DuplicateEntryException;
    boolean updateCategory(int categoryID, String categoryName, String description) throws DuplicateEntryException;
    boolean deleteCategory(int categoryID) throws MySQLIntegrityConstraintViolationException;
    List<Category> findAllCategories();
    boolean addPenaltyTariff(int penaltyID, BigDecimal fixedPrice, LocalDate fromDate, LocalDate toDate);
    boolean updatePenaltyTariff(int tariffID, int penaltyID, BigDecimal fixedPrice, LocalDate fromDate, LocalDate toDate);
    boolean deletePenaltyTariff(int tariffID) throws MySQLIntegrityConstraintViolationException;
    List<PenaltyTariff> findPenaltyTariffs();
    List<PenaltyTariff> findPenaltyTariffsByID(int penaltyID);
}
