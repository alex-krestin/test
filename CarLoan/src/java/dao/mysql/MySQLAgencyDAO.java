package dao.mysql;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.entity.AgencyDAO;
import dao.exception.DuplicateEntryException;
import entity.Agency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static dao.mysql.MySQLDAOFactory.*;


@SuppressWarnings("LawOfDemeter")
public class MySQLAgencyDAO extends MySQLCommonQueries implements AgencyDAO{
    private static final Logger log = Logger.getLogger(MySQLAgencyDAO.class.getName());

    @Override
    public boolean addAgency(String agencyCode, String city, String address, String tel, String fax, String email) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;

        String query = "INSERT INTO agencies (agencyCode, city, address, tel, fax, email)"
                + " VALUES (?, ?, ?, ?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, agencyCode);
            statement.setString(2, city);
            statement.setString(3, address);
            statement.setString(4, tel);
            statement.setString(5, fax);
            statement.setString(6, email);

            if (statement.executeUpdate() == 1)
                result = true;

        } catch (MySQLIntegrityConstraintViolationException e) {
            throw new DuplicateEntryException(e);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public boolean updateAgency(int agencyID, String agencyCode, String city, String address, String tel, String fax, String email) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;
        String query = "UPDATE agencies SET agencyCode=?, city=?, address=?, tel=?, fax=?, email=? WHERE agencyID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, agencyCode);
            statement.setString(2, city);
            statement.setString(3, address);
            statement.setString(4, tel);
            statement.setString(5, fax);
            statement.setString(6, email);
            statement.setInt(7, agencyID);

            if (statement.executeUpdate() == 1)
                result = true;

        } catch (MySQLIntegrityConstraintViolationException e) {
            throw new DuplicateEntryException(e);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public boolean deleteAgency(int agencyID) throws MySQLIntegrityConstraintViolationException{
        return deleteRecord("agencies", "agencyID", agencyID);
    }

    @Override
    public boolean updateAgencyState(int agencyID, boolean active) {
        return updateBooleanField("agencies", "active", "agencyID", agencyID, active);
    }


    @Override
    public List<Agency> findAllAgencies() {
        List<Agency> result = new ArrayList<>();
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM agencies WHERE active = TRUE";

        try {
            statement = conn.prepareStatement(query);

            if (statement.execute(query)) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    Agency agency = new Agency.Builder()
                            .id(rs.getInt("agencyID"))
                            .agencyCode(rs.getString("agencyCode"))
                            .city(rs.getString("city"))
                            .address(rs.getString("address"))
                            .phoneNumber(rs.getString("tel"))
                            .faxNumber(rs.getString("fax"))
                            .email(rs.getString("email")).build();

                    result.add(agency);
                }
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }
}
