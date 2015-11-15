package dao.mysql;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import dao.entity.AccessoryDAO;
import entity.Accessory;
import entity.Agency;
import entity.Category;

import java.sql.*;
import java.util.ArrayList;


public class MySQLAccessoryDAO implements AccessoryDAO{
    @Override
    public boolean addAccessory(int categoryID, String title, String description, String inventoryCode, int currentAgencyID)
            throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;

        String query = "INSERT INTO accessories (categoryID, title, description, inventoryCode, currentAgencyID) "
                + " VALUES (?, ?, ?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, categoryID);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.setString(4, inventoryCode);
            statement.setInt(5, currentAgencyID);

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
    public boolean updateAccessory(int accessoryID, int categoryID, String title, String description,
                                   String inventoryCode, int currentAgencyID) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "UPDATE accessories SET categoryID=?, title=?, description=?, inventoryCode=?, currentAgencyID=? WHERE accessoryID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, categoryID);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.setString(4, inventoryCode);
            statement.setInt(5, currentAgencyID);
            statement.setInt(6, accessoryID);

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
    public boolean deleteAccessory(int accessoryID) throws MySQLIntegrityConstraintViolationException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "DELETE FROM accessories WHERE accessoryID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, accessoryID);

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
    public boolean changeAccessoryStatus(int accessoryID, boolean available) {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "UPDATE accessories SET available=? WHERE accessoryID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setBoolean(1, available);
            statement.setInt(2, accessoryID);

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
    public ArrayList<Accessory> getAllAccessories() {
        ArrayList<Accessory> result = new ArrayList<>();
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM accessories, accessory_categories, agencies WHERE accessories.categoryID = " +
                "accessory_categories.categoryID AND accessories.currentAgencyID = agencies.agencyID" +
                " AND available = TRUE";

        try {
            statement = conn.prepareStatement(query);

            if (statement.execute(query)) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    int accessoryID = rs.getInt("accessoryID");
                    int categoryID = rs.getInt("categoryID");
                    String categoryName = rs.getString("categoryName");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    String inventoryCode = rs.getString("inventoryCode");
                    int agencyID = rs.getInt("agencyID");
                    String agencyCode = rs.getString("agencyCode");
                    String city = rs.getString("city");
                    String address = rs.getString("address");
                    String tel = rs.getString("tel");
                    String fax = rs.getString("fax");
                    String email = rs.getString("email");

                    Category category = new Category(categoryID, categoryName);
                    Agency currentAgency = new Agency(agencyID, agencyCode, city, address, tel, fax, email);
                    Accessory accessory = new Accessory(accessoryID, category, title, description, inventoryCode, currentAgency);

                    result.add(accessory);
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

        String query = "INSERT INTO accessory_categories (categoryName, categoryDescription) "
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
    public boolean updateCategory(int categoryID, String categoryName, String description)
            throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "UPDATE accessory_categories SET categoryName=?, categoryDescription=? WHERE categoryID=?";

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
        String query = "DELETE FROM accessory_categories WHERE categoryID=?";

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
        String query = "SELECT * FROM accessory_categories";

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
