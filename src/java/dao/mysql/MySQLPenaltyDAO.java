package dao.mysql;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import dao.entity.PenaltyDAO;
import entity.Category;
import entity.Penalty;

import java.sql.*;
import java.util.ArrayList;


public class MySQLPenaltyDAO implements PenaltyDAO {
    @Override
    public boolean addPenalty(int categoryID, String title, String description) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;

        String query = "INSERT INTO penalties (categoryID, title, description) "
                + " VALUES (?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, categoryID);
            statement.setString(2, title);
            statement.setString(3, description);

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
    public boolean updatePenalty(int penaltyID, int categoryID, String title, String description) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "UPDATE penalties SET categoryID=?, title=?, description=? WHERE penaltyID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, categoryID);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.setInt(4, penaltyID);

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
    public boolean deletePenalty(int penaltyID) throws MySQLIntegrityConstraintViolationException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "DELETE FROM penalties WHERE penaltyID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, penaltyID);

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
    public boolean changePenaltyStatus(int penaltyID, boolean available) {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "UPDATE penalties SET available=? WHERE penaltyID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setBoolean(1, available);
            statement.setInt(2, penaltyID);

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
    public ArrayList<Penalty> getAllPenalties() {
        ArrayList<Penalty> result = new ArrayList<>();
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM penalties, penalty_categories WHERE penalties.categoryID = " +
                "penalty_categories.categoryID AND available = TRUE";

        try {
            statement = conn.prepareStatement(query);

            if (statement.execute(query)) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    int penaltyID = rs.getInt("penaltyID");
                    int categoryID = rs.getInt("categoryID");
                    String categoryName = rs.getString("categoryName");
                    String title = rs.getString("title");
                    String description = rs.getString("description");

                    Category category = new Category(categoryID, categoryName);
                    Penalty penalty = new Penalty(penaltyID, title, description, category);

                    result.add(penalty);
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
    public boolean addCategory(String categoryName, String description) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;

        String query = "INSERT INTO penalty_categories (categoryName, categoryDescription) "
                + " VALUES (?, ?)";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, categoryName);
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
    public boolean updateCategory(int categoryID, String categoryName, String description) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "UPDATE penalty_categories SET categoryName=?, categoryDescription=? WHERE categoryID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, categoryName);
            statement.setString(2, description);
            statement.setInt(3, categoryID);

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
    public boolean deleteCategory(int categoryID) throws MySQLIntegrityConstraintViolationException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "DELETE FROM penalty_categories WHERE categoryID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, categoryID);

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
    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> result = new ArrayList<>();
        Connection conn = MySQLDAOFactory.connect();
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM penalty_categories";

        try {
            statement = conn.createStatement();

            if (statement.execute(query)) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    int categoryID = rs.getInt("categoryID");
                    String categoryName = rs.getString("categoryName");
                    String description = rs.getString("categoryDescription");

                    Category category = new Category(categoryID, categoryName, description);

                    result.add(category);
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
