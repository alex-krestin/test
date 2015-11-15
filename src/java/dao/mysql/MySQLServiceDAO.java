package dao.mysql;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import dao.entity.ServiceDAO;
import entity.Service;

import java.sql.*;
import java.util.ArrayList;


public class MySQLServiceDAO implements ServiceDAO{
    @Override
    public boolean addService(String title, String description) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;

        String query = "INSERT INTO services (title, description) "
                + " VALUES (?, ?)";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, description);

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
    public boolean updateService(int serviceID, String title, String description) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "UPDATE services SET title=?, description=? WHERE serviceID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setInt(3, serviceID);

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
    public boolean deleteService(int serviceID) throws MySQLIntegrityConstraintViolationException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "DELETE FROM services WHERE serviceID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, serviceID);

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
    public boolean changeServiceStatus(int serviceID, boolean available) {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "UPDATE services SET available=? WHERE serviceID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setBoolean(1, available);
            statement.setInt(2, serviceID);

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
    public ArrayList<Service> getAllServices() {
        ArrayList<Service> result = new ArrayList<>();
        Connection conn = MySQLDAOFactory.connect();
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM services WHERE available = TRUE";

        try {
            statement = conn.createStatement();

            if (statement.execute(query)) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    int serviceID = rs.getInt("serviceID");
                    String title = rs.getString("title");
                    String description = rs.getString("description");

                    Service service = new Service(serviceID, title, description);
                    result.add(service);
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
