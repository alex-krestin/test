package it.uniba.carloan.dao.mysql;


import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class MySQLAbstractCategoryDAO extends MySQLAbstractDAO<Category, Integer> {

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Category object) throws PersistenceException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getDescription());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Category object) throws PersistenceException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getDescription());
            statement.setInt(3, object.getId());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected List<Category> parseResultSet(ResultSet rs) throws PersistenceException {
        List<Category> result = new ArrayList<>();
        if (rs != null) {
            Category category;

            try {
                while (rs.next()) {
                    int categoryID = rs.getInt("categoryID");
                    String categoryName = rs.getString("categoryName");
                    String description = rs.getString("categoryDescription");

                    category = new Category.Builder(categoryName)
                            .id(categoryID)
                            .description(description).build();

                    result.add(category);
                }
            } catch (SQLException e) {
                throw new PersistenceException(e);
            }
        }

        return result;
    }
}
