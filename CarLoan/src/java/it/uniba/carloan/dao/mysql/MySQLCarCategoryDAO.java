package it.uniba.carloan.dao.mysql;


import it.uniba.carloan.dao.entity.GenericDAO;
import it.uniba.carloan.entity.Category;

public class MySQLCarCategoryDAO extends MySQLAbstractCategoryDAO implements GenericDAO<Category, Integer> {

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO car_categories (categoryName, categoryDescription) VALUES (?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE car_categories SET categoryName=?, categoryDescription=? WHERE categoryID=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM car_categories WHERE categoryID=?";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM car_categories";
    }

    @Override
    protected String getFindByPKQuery() {
        return "SELECT * FROM car_categories WHERE categoryID=?";
    }
}
