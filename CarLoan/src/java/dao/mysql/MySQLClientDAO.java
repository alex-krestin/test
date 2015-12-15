package dao.mysql;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.entity.ClientDAO;
import dao.exception.DuplicateEntryException;
import entity.Client;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static dao.mysql.MySQLDAOFactory.*;

@SuppressWarnings("LawOfDemeter")
public class MySQLClientDAO extends MySQLCommonQueries implements ClientDAO {
    private static final Logger log = Logger.getLogger(MySQLClientDAO.class.getName());

    @Override
    public boolean addClient(String name, String surname, String sex, String fiscalCode, LocalDate birthday,
                             String phoneNumber, String comment) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;
        String query = "INSERT INTO clients (name, surname, sex, fiscalCode, bday, phone, comment) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, sex);
            statement.setString(4, fiscalCode);
            statement.setDate(5, Date.valueOf(birthday));
            statement.setString(6, phoneNumber);
            statement.setString(7, comment);

            if (statement.executeUpdate() == 1) {
                result = true;
            }

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
    public boolean updateClient(int clientID, String name, String surname, String sex, String fiscalCode, LocalDate birthday,
                                String phoneNumber, String comment) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;
        String query = "UPDATE clients SET name=?, surname=?, sex=?, fiscalCode=?, bday=?, phone=?, " +
                "comment=? WHERE clientID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, sex);
            statement.setString(4, fiscalCode);
            statement.setDate(5, Date.valueOf(birthday));
            statement.setString(6, phoneNumber);
            statement.setString(7, comment);
            statement.setInt(8, clientID);
            statement.execute();

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
    public boolean deleteClient(int clientID) throws MySQLIntegrityConstraintViolationException{
        return deleteRecord("clients", "clientID", clientID);
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> result = new ArrayList<>();
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM clients";

        try {
            statement = conn.prepareStatement(query);

            if (statement.execute(query)) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    Client client = new Client.Builder()
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

    @Override
    public Client findClientByFiscalCode(String fiscalCode) {
        Client result = null;
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM clients WHERE fiscalCode=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, fiscalCode);

            if (statement.execute()) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    result = new Client.Builder()
                            .id(rs.getInt("clientID"))
                            .name(rs.getString("name"))
                            .surname(rs.getString("surname"))
                            .sex(rs.getString("sex"))
                            .fiscalCode(fiscalCode)
                            .birthday(rs.getDate("bday").toLocalDate())
                            .phoneNumber(rs.getString("phone"))
                            .comment(rs.getString("comment")).build();
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
