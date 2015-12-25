package it.uniba.carloan.dao.mysql;


import it.uniba.carloan.dao.entity.PenaltyDAO;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Category;
import it.uniba.carloan.entity.Penalty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLPenaltyDAO extends MySQLAbstractDAO<Penalty, Integer> implements PenaltyDAO {

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO penalties (categoryID, title, description) VALUES (?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE penalties SET categoryID=?, title=?, description=? WHERE penaltyID=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM penalties WHERE penaltyID=?";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM penalties, penalty_categories WHERE penalties.categoryID = " +
                "penalty_categories.categoryID AND available IS TRUE";
    }

    @Override
    protected String getFindByPKQuery() {
        return "SELECT * FROM penalties, penalty_categories WHERE penalties.categoryID = " +
                "penalty_categories.categoryID AND penaltyID=?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Penalty object) throws PersistenceException {
        try {
            statement.setInt(1, object.getCategoryID());
            statement.setString(2, object.getTitle());
            statement.setString(3, object.getDescription());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Penalty object) throws PersistenceException {
        try {
            statement.setInt(1, object.getCategoryID());
            statement.setString(2, object.getTitle());
            statement.setString(3, object.getDescription());
            statement.setInt(4, object.getId());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @SuppressWarnings("LawOfDemeter")
    @Override
    protected List<Penalty> parseResultSet(ResultSet rs) throws PersistenceException {
        List<Penalty> result = new ArrayList<>();

        if (rs != null) {
            Category category;
            Penalty penalty;

            try {
                while (rs.next()) {
                    category = new Category.Builder(rs.getString("categoryName"))
                            .id(rs.getInt("categoryID")).build();

                    penalty = new Penalty.Builder()
                            .id(rs.getInt("penaltyID"))
                            .title(rs.getString("title"))
                            .description(rs.getString("description"))
                            .category(category).build();

                    result.add(penalty);
                }
            } catch (SQLException e) {
                throw new PersistenceException(e);
            }
        }

        return result;
    }

    @Override
    public boolean updateAvailability(Penalty object) throws PersistenceException {
        String query = "UPDATE penalties SET available=? WHERE penaltyID=?";
        return updateGenericField(query, object.isAvailable(), object.getId());
    }
}
