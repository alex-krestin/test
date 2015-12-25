package it.uniba.carloan.dao.mysql;


import it.uniba.carloan.dao.entity.ServiceDAO;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLServiceDAO extends MySQLAbstractDAO<Service, Integer> implements ServiceDAO {

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO services (title, description) VALUES (?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE services SET title=?, description=? WHERE serviceID=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM services WHERE serviceID=?";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM services WHERE available IS TRUE";
    }

    @Override
    protected String getFindByPKQuery() {
        return "SELECT * FROM services WHERE serviceID=?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Service object) throws PersistenceException {
        try {
            statement.setString(1, object.getTitle());
            statement.setString(2, object.getDescription());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Service object) throws PersistenceException {
        try {
            statement.setString(1, object.getTitle());
            statement.setString(2, object.getDescription());
            statement.setInt(3, object.getId());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @SuppressWarnings("LawOfDemeter")
    @Override
    protected List<Service> parseResultSet(ResultSet rs) throws PersistenceException {
        List<Service> result = new ArrayList<>();

        if (rs != null) {
            Service service;

            try {
                while (rs.next()) {
                    service = new Service.Builder()
                            .id(rs.getInt("serviceID"))
                            .title(rs.getString("title"))
                            .description(rs.getString("description")).build();

                    result.add(service);
                }
            } catch (SQLException e) {
                throw new PersistenceException(e);
            }
        }

        return result;
    }

    @Override
    public boolean updateAvailability(Service object) throws PersistenceException {
        String query = "UPDATE services SET available=? WHERE serviceID=?";
        return updateGenericField(query, object.isAvailable(), object.getId());
    }
}
