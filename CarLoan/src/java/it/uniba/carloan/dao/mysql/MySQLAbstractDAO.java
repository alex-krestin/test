package it.uniba.carloan.dao.mysql;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import it.uniba.carloan.dao.entity.GenericDAO;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.exception.RecordNotFoundException;
import it.uniba.carloan.entity.Identified;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public abstract class MySQLAbstractDAO<T extends Identified<PK>, PK extends Serializable>
        implements GenericDAO<T, PK> {

    protected Connection conn = getActiveConnection();

    protected abstract String getInsertQuery();
    protected abstract String getUpdateQuery();
    protected abstract String getDeleteQuery();
    protected abstract String getFindAllQuery();
    protected abstract String getFindByPKQuery();
    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws PersistenceException;
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws PersistenceException;
    protected abstract List<T> parseResultSet(ResultSet rs) throws PersistenceException;

    @Override
    public Integer add(T object) throws PersistenceException, DuplicateEntryException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = getInsertQuery();
        Integer recordID = null;

        try {
            statement = conn.prepareStatement(query, RETURN_GENERATED_KEYS);
            prepareStatementForInsert(statement, object);

            int count = statement.executeUpdate();

            if (count != 1) {
                throw new PersistenceException("On insert were modified more then 1 record: " + count);
            }

            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                recordID = rs.getInt(1);
            }
            else {
                throw new PersistenceException("Error on record inserting.");
            }

        } catch (MySQLIntegrityConstraintViolationException e) {
            throw new DuplicateEntryException(e);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            closeStatement(statement);
            closeResultSet(rs);
        }

        return recordID;
    }

    @Override
    public boolean update(T object) throws PersistenceException, DuplicateEntryException {
        PreparedStatement statement = null;
        String query = getUpdateQuery();

        try {
            statement = conn.prepareStatement(query);
            prepareStatementForUpdate(statement, object);

            int count = statement.executeUpdate();

            if (count != 1) {
                throw new PersistenceException("On update were modified more then 1 record: " + count);
            }

        } catch (MySQLIntegrityConstraintViolationException e) {
            throw new DuplicateEntryException(e);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            closeStatement(statement);
        }

        return true;
    }

    @Override
    public boolean delete(T object) throws PersistenceException, IntegrityConstraintViolationException {
        PreparedStatement statement = null;
        String query = getDeleteQuery();

        try {
            statement = conn.prepareStatement(query);
            statement.setObject(1, object.getId());

            int count = statement.executeUpdate();

            if (count != 1) {
                throw new PersistenceException("Were deleted more then 1 record: " + count);
            }
        } catch (MySQLIntegrityConstraintViolationException e) {
            throw new IntegrityConstraintViolationException(e);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            closeStatement(statement);
        }

        return true;
    }

    @Override
    public List<T> findAll() throws PersistenceException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<T> list;
        String query = getFindAllQuery();

        try {
            statement = conn.prepareStatement(query);
            rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            closeStatement(statement);
            closeResultSet(rs);
        }

        return list;
    }

    public T findByPK(PK primaryKey) throws PersistenceException, RecordNotFoundException {
        List<T> list = findByKey(getFindByPKQuery(), primaryKey);

        int count = list.size();

        if (count == 0) {
            throw new RecordNotFoundException();
        }
        else if (count > 1) {
            throw new PersistenceException("Were find more then 1 record: " + count);
        }


        return list.iterator().next();
    }

    protected boolean updateGenericField(String query, Object value, PK primaryKey) throws PersistenceException {
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(query);
            statement.setObject(1, value);
            statement.setObject(2, primaryKey);

            int count = statement.executeUpdate();

            if (count != 1) {
                throw new PersistenceException("On update were modified more then 1 record: " + count);
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            closeStatement(statement);
        }
        return true;
    }

    protected List<T> findByKey(String query, Object key) throws PersistenceException {
        List<T> result = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = conn.prepareStatement(query);
            statement.setObject(1, key);

            if (statement.execute()) {
                rs = statement.getResultSet();
                result = parseResultSet(rs);
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
        }

        return result;
    }

}
