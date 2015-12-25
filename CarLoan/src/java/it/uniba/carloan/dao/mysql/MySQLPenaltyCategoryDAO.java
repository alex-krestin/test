package it.uniba.carloan.dao.mysql;


import it.uniba.carloan.dao.entity.GenericDAO;
import it.uniba.carloan.entity.Category;

public class MySQLPenaltyCategoryDAO extends MySQLAbstractCategoryDAO implements GenericDAO<Category, Integer> {

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO penalty_categories (categoryName, categoryDescription) VALUES (?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE penalty_categories SET categoryName=?, categoryDescription=? WHERE categoryID=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM penalty_categories WHERE categoryID=?";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM penalty_categories";
    }

    @Override
    protected String getFindByPKQuery() {
        return "SELECT * FROM penalty_categories WHERE categoryID=?";
    }
}
