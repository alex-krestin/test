package dao.mysql;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import dao.entity.ClientDAO;
import entity.Client;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MySQLClientDAO implements ClientDAO {
    @Override
    public boolean addClient(String name, String surname, String sex, String fiscalCode, Date birthday,
                             String phoneNumber, String comment) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "INSERT INTO clients (name, surname, sex, fiscalCode, bday, phone, comment) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, sex);
            statement.setString(4, fiscalCode);
            statement.setDate(5, birthday);
            statement.setString(6, phoneNumber);
            statement.setString(7, comment);

            if (statement.executeUpdate() == 1) {
                result = true;
            }

        } catch (MySQLIntegrityConstraintViolationException e) {
            throw new DuplicateEntryException(e);
        } catch (SQLException ex) {
            // handle exception
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    @Override
    public boolean updateClient(int clientID, String name, String surname, String sex, String fiscalCode, Date birthday,
                                String phoneNumber, String comment) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "UPDATE clients SET name=?, surname=?, sex=?, fiscalCode=?, bday=?, phone=?, " +
                "comment=? WHERE clientID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, sex);
            statement.setString(4, fiscalCode);
            statement.setDate(5, birthday);
            statement.setString(6, phoneNumber);
            statement.setString(7, comment);
            statement.setInt(8, clientID);
            statement.execute();

            if (statement.executeUpdate() == 1)
                result = true;

        } catch (MySQLIntegrityConstraintViolationException e) {
            throw new DuplicateEntryException(e);
        } catch (SQLException ex) {
            // handle exception
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    @Override
    public boolean deleteClient(int clientID) throws MySQLIntegrityConstraintViolationException{
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "DELETE FROM clients WHERE clientID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, clientID);

            if (statement.executeUpdate() == 1)
                result = true;

        } catch (SQLException ex) {
            // handle exception
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    @Override
    public ArrayList<Client> getAllClients() {
        ArrayList<Client> result = new ArrayList<>();
        Connection conn = MySQLDAOFactory.connect();
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM clients";

        try {
            statement = conn.createStatement();

            if (statement.execute(query)) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    int clientID = rs.getInt("clientID");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String sex = rs.getString("sex");
                    String fiscalCode = rs.getString("fiscalCode");
                    java.util.Date birthday = rs.getTimestamp("bday");
                    String phone = rs.getString("phone");
                    String comment = rs.getString("comment");

                    Client client = new Client(clientID, name, surname, sex, fiscalCode, birthday, phone, comment);
                    result.add(client);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                } // ignore

            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                } // ignore

            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    @Override
    public Client searchClient(String fiscalCode) {
        Client result = null;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM clients WHERE fiscalCode=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, fiscalCode);

            if (statement.execute(query)) {
                rs = statement.getResultSet();
            }

            if (rs != null && rs.getFetchSize() == 1) {
                while (rs.next()) {
                    int clientID = rs.getInt("clientID");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String sex = rs.getString("sex");
                    java.util.Date birthday = rs.getTimestamp("bday");
                    String phone = rs.getString("phone");
                    String comment = rs.getString("comment");

                    result = new Client(clientID, name, surname, sex, fiscalCode, birthday, phone, comment);
                }
            }

        } catch (SQLException e) {
            // handle any errors
        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                } // ignore

            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                } // ignore

            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
