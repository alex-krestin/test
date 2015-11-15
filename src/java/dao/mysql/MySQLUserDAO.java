package dao.mysql;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import dao.entity.UserDAO;
import entity.Agency;
import entity.User;

import java.sql.*;
import java.util.ArrayList;


public class MySQLUserDAO implements UserDAO {

    @Override
    public boolean addUser(String name, String surname, String username, String password, String accountType,
                           boolean accessGranted, int agencyID) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "INSERT INTO users (name, surname, username, password, accountType, accessGranted, agencyID)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.setString(5, accountType);
            statement.setBoolean(6, accessGranted);
            statement.setInt(7, agencyID);

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
    public boolean updateUser(int userID, String name, String surname, String username, String accountType, int agencyID) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "UPDATE users SET name=?, surname=?, username=?, accountType=?, agencyID=? WHERE userID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, username);
            statement.setString(4, accountType);
            statement.setInt(5, agencyID);
            statement.setInt(6, userID);

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
    public boolean deleteUser(int userID) {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "DELETE FROM users WHERE userID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, userID);

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
    public boolean changeUserPassword(int userID, String password) {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "UPDATE users SET password=? WHERE userID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, password);
            statement.setInt(2, userID);

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
    public boolean changeAccess(int userID, boolean accessGranted) {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        String query = "UPDATE users SET accessGranted=? WHERE userID=?";
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(query);
            statement.setBoolean(1, accessGranted);
            statement.setInt(2, userID);

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
    public ArrayList<User> getAllUsers() {
        ArrayList<User> result = new ArrayList<>();
        Connection conn = MySQLDAOFactory.connect();
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM users, agencies WHERE users.agencyID = agencies.agencyID";

        try {
            statement = conn.createStatement();

            if (statement.execute(query)) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    int userID = rs.getInt("userID");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String accountType = rs.getString("accountType");
                    Boolean accessGranted = rs.getBoolean("accessGranted");
                    int agencyID = rs.getInt("agencyID");
                    String agencyCode = rs.getString("agencyCode");
                    String city = rs.getString("city");
                    String address = rs.getString("address");
                    String tel = rs.getString("tel");
                    String fax = rs.getString("fax");
                    String email = rs.getString("email");

                    Agency agency = new Agency(agencyID, agencyCode, city, address, tel, fax, email);
                    User user = new User(userID, name, surname, username, password, accountType, accessGranted, agency);

                    result.add(user);
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


    @Override
    public boolean isExists(String username) {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT COUNT(*) AS total FROM users WHERE username = ?";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, username);

            if (statement.execute()) {
                rs = statement.getResultSet();
            }

            if (rs != null && rs.next()) {
                result = rs.getBoolean(1);
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

        }

        return result;
    }

}
