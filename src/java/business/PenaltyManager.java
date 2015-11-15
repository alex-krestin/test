package business;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.DAOFactory;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import dao.entity.PenaltyDAO;
import entity.Category;
import entity.Penalty;
import entity.TransferObject;
import utility.SessionData;

import java.util.ArrayList;


public class PenaltyManager {
    private PenaltyDAO dao;

    public PenaltyManager() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getPenaltyDAO();
    }

    public TransferObject getAllPenalties() {
        ArrayList<Penalty> penalties = dao.getAllPenalties();
        return new TransferObject(penalties);
    }

    public TransferObject addPenalty(Penalty penalty) throws DuplicateEntryException {
        boolean result;

        result = dao.addPenalty(penalty.getCategory().getId(), penalty.getTitle(), penalty.getDescription());

        return new TransferObject(result);
    }

    public TransferObject updatePenalty(Penalty penalty) throws DuplicateEntryException {
        boolean result;

        result = dao.updatePenalty(penalty.getId(), penalty.getCategory().getId(), penalty.getTitle(), penalty.getDescription());

        return new TransferObject(result);
    }

    public TransferObject deletePenalty(Penalty penalty) throws MySQLIntegrityConstraintViolationException {
        boolean result;

        result = dao.deletePenalty(penalty.getId());

        return new TransferObject(result);
    }

    public TransferObject changePenaltyState(Penalty penalty) {
        boolean result;

        if(penalty.isAvailable()) {
            penalty.setAvailable(false);
        }
        else {
            penalty.setAvailable(true);
        }
        result = dao.changePenaltyStatus(penalty.getId(), penalty.isAvailable());
        return new TransferObject(result);
    }

    public TransferObject getAllCategories() {
        ArrayList<Category> categories = dao.getAllCategories();
        return new TransferObject(categories);
    }

    public TransferObject addCategory(Category category) throws DuplicateEntryException {
        boolean result;

        result = dao.addCategory(category.getName(), category.getDescription());

        return new TransferObject(result);
    }

    public TransferObject updateCategory(Category category) throws DuplicateEntryException {
        boolean result;

        result = dao.updateCategory(category.getId(), category.getName(), category.getDescription());

        return new TransferObject(result);
    }

    public TransferObject deleteCategory(Category category) throws MySQLIntegrityConstraintViolationException {
        boolean result;

        result = dao.deleteCategory(category.getId());

        return new TransferObject(result);
    }
}
