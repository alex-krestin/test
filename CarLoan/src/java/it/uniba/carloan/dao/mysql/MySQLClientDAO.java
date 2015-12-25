package it.uniba.carloan.dao.mysql;


import it.uniba.carloan.dao.entity.ClientDAO;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.exception.RecordNotFoundException;
import it.uniba.carloan.entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.*;

public class MySQLClientDAO extends MySQLAbstractDAO<Client, Integer> implements ClientDAO{

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO clients (name, surname, sex, fiscalCode, bday, phone, comment) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE clients SET name=?, surname=?, sex=?, fiscalCode=?, bday=?, phone=?, " +
                "comment=? WHERE clientID=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM clients WHERE clientID=?";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM clients";
    }

    @Override
    protected String getFindByPKQuery() {
        return "SELECT FROM clients WHERE clientID=?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Client object) throws PersistenceException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getSurname());
            statement.setString(3, object.getSex());
            statement.setString(4, object.getFiscalCode());
            statement.setDate(5, Date.valueOf(object.getBirthday()));
            statement.setString(6, object.getPhoneNumber());
            statement.setString(7, object.getComment());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Client object) throws PersistenceException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getSurname());
            statement.setString(3, object.getSex());
            statement.setString(4, object.getFiscalCode());
            statement.setDate(5, Date.valueOf(object.getBirthday()));
            statement.setString(6, object.getPhoneNumber());
            statement.setString(7, object.getComment());
            statement.setInt(8, object.getId());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @SuppressWarnings("LawOfDemeter")
    @Override
    protected List<Client> parseResultSet(ResultSet rs) throws PersistenceException {
        List<Client> result = new ArrayList<>();

        if (rs != null) {
            Client client;

            try {
                while (rs.next()) {
                    client = new Client.Builder()
                            .id(rs.getInt("clientID"))
                            .name(rs.getString("name"))
                            .surname(rs.getString("surname"))
                            .sex(rs.getString("sex"))
                            .fiscalCode(rs.getString("fiscalCode"))
                            .birthday(rs.getDate("bday").toLocalDate())
                            .phoneNumber(rs.getString("phone"))
                            .comment(rs.getString("comment")).build();

                    result.add(client);
                }
            } catch (SQLException e) {
                throw new PersistenceException(e);
            }
        }

        return result;
    }

    @Override
    public Client findByFiscalCode(Client object) throws PersistenceException, RecordNotFoundException {
        List<Client> result = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String query = "SELECT * FROM clients WHERE fiscalCode=?";
            statement = conn.prepareStatement(query);
            statement.setString(1, object.getFiscalCode());

            if (statement.execute()) {
                rs = statement.getResultSet();
                result = parseResultSet(rs);
            }

            if (result == null || result.size() == 0) {
                throw new RecordNotFoundException();
            }

            if (result.size() > 1) {
                throw new PersistenceException("Received more then 1 record: " + result.size());
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(conn);
        }

        return result.iterator().next();
    }
}
