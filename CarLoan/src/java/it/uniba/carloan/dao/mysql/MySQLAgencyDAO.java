package it.uniba.carloan.dao.mysql;


import it.uniba.carloan.dao.entity.AgencyDAO;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Agency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLAgencyDAO extends MySQLAbstractDAO<Agency, Integer> implements AgencyDAO {

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO agencies (agencyCode, city, address, tel, fax, email)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE agencies SET agencyCode=?, city=?, address=?, tel=?, fax=?, email=? WHERE agencyID=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM agencies WHERE agencyID=?";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM agencies WHERE active = TRUE";
    }

    @Override
    protected String getFindByPKQuery() {
        return "SELECT * FROM agencies WHERE agencyID=?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Agency object) throws PersistenceException {
        try {
            statement.setString(1, object.getAgencyCode());
            statement.setString(2, object.getCity());
            statement.setString(3, object.getAddress());
            statement.setString(4, object.getPhoneNumber());
            statement.setString(5, object.getFaxNumber());
            statement.setString(6, object.getEmail());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Agency object) throws PersistenceException {
        try {
            statement.setString(1, object.getAgencyCode());
            statement.setString(2, object.getCity());
            statement.setString(3, object.getAddress());
            statement.setString(4, object.getPhoneNumber());
            statement.setString(5, object.getFaxNumber());
            statement.setString(6, object.getEmail());
            statement.setInt(7, object.getId());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected List<Agency> parseResultSet(ResultSet rs) throws PersistenceException {
        List<Agency> result = new ArrayList<>();

        if (rs != null) {
            Agency agency;

            try {
                while (rs.next()) {
                    agency = new Agency.Builder()
                            .id(rs.getInt("agencyID"))
                            .agencyCode(rs.getString("agencyCode"))
                            .city(rs.getString("city"))
                            .address(rs.getString("address"))
                            .phoneNumber(rs.getString("tel"))
                            .faxNumber(rs.getString("fax"))
                            .email(rs.getString("email")).build();

                    result.add(agency);
                }
            } catch (SQLException e) {
                throw new PersistenceException(e);
            }
        }

        return result;
    }

    @Override
    public boolean updateState(Agency object) throws PersistenceException {
        String query = "UPDATE agencies SET active=? WHERE agencyID=?";
        return updateGenericField(query, object.isActive(), object.getId());
    }
}
