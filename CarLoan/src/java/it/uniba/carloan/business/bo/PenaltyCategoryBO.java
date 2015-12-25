package it.uniba.carloan.business.bo;


import it.uniba.carloan.dao.DAOFactory;
import it.uniba.carloan.dao.entity.GenericDAO;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Category;

import java.util.List;

public class PenaltyCategoryBO {
    private GenericDAO<Category, Integer> dao;

    public PenaltyCategoryBO() throws PersistenceException {
        this.dao = DAOFactory.getDAOFactory().getPenaltyCategoryDAO();
    }

    public Integer addCategory(Category category) throws DuplicateEntryException, PersistenceException {
        return dao.add(category);
    }

    public boolean updateCategory(Category category) throws DuplicateEntryException, PersistenceException {
        return dao.update(category);
    }

    public boolean deleteCategory(Category category) throws IntegrityConstraintViolationException, PersistenceException {
        return dao.delete(category);
    }

    public List<Category> getAllCategories() throws PersistenceException {
        return dao.findAll();
    }
}
