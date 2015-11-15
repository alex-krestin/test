package dao.mysql;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import dao.entity.AgencyDAO;
import entity.Agency;

import java.sql.*;
import java.util.ArrayList;


public class MySQLAgencyDAO implements AgencyDAO{

    @Override
    public boolean addAgency(String agencyCode, String city, String address, String tel, String fax, String email) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
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
            e.printStackTrace();
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
    public boolean updateAgency(int agencyID, String agencyCode, String city, String address, String tel, String fax, String email) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
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
            e.printStackTrace();
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
    public boolean deleteAgency(int agencyID) throws MySQLIntegrityConstraintViolationException{
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "DELETE FROM agencies WHERE agencyID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, agencyID);

            if (statement.executeUpdate() == 1)
                result = true;

        } catch (MySQLIntegrityConstraintViolationException e) {
            throw new MySQLIntegrityConstraintViolationException();
        } catch (SQLException e) {
            e.printStackTrace();
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
    public boolean changeAgencyState(int agencyID, boolean active) {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "UPDATE agencies SET active=? WHERE agencyID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setBoolean(1, active);
            statement.setInt(2, agencyID);

            if (statement.executeUpdate() == 1)
                result = true;

        } catch (SQLException e) {
            e.printStackTrace();
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
    public ArrayList<Agency> getAllAgencies() {
        ArrayList<Agency> result = new ArrayList<>();
        Connection conn = MySQLDAOFactory.connect();
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM agencies WHERE active = TRUE";

        try {
            statement = conn.createStatement();

            if (statement.execute(query)) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    int agencyID = rs.getInt("agencyID");
                    String agencyCode = rs.getString("agencyCode");
                    String city = rs.getString("city");
                    String address = rs.getString("address");
                    String tel = rs.getString("tel");
                    String fax = rs.getString("fax");
                    String email = rs.getString("email");

                    Agency agency = new Agency(agencyID, agencyCode, city, address, tel, fax, email);

                    result.add(agency);
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
