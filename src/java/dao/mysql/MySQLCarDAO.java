package dao.mysql;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import dao.entity.CarDAO;
import entity.Agency;
import entity.Car;
import entity.Category;

import java.sql.*;
import java.util.ArrayList;

public class MySQLCarDAO implements CarDAO {

    @Override
    public boolean addCar(String brand, String model, int year, int categoryID, int currentAgencyID, String plate,
                          int doors, int passengers, String description, int mileage) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;

        String query = "INSERT INTO cars (brand, model, manufacture_year, plate, categoryID, doors, passengers, description, mileage, currentAgencyID)  "
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, brand);
            statement.setString(2, model);
            statement.setInt(3, year);
            statement.setString(4, plate);
            statement.setInt(5, categoryID);
            statement.setInt(6, doors);
            statement.setInt(7, passengers);
            statement.setString(8, description);
            statement.setInt(9, mileage);
            statement.setInt(10, currentAgencyID);


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
    public boolean updateCar(int carID, String brand, String model, int year, int categoryID, String plate, int doors,
                             int passengers, String description) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "UPDATE cars SET brand=?, model=?, manufacture_year=?, plate=?, categoryID=?, doors=?, passengers=?, description=? WHERE carID=?";

        try {
            statement = conn.prepareStatement(query);
            statement = conn.prepareStatement(query);
            statement.setString(1, brand);
            statement.setString(2, model);
            statement.setInt(3, year);
            statement.setString(4, plate);
            statement.setInt(5, categoryID);
            statement.setInt(6, doors);
            statement.setInt(7, passengers);
            statement.setString(8, description);
            statement.setInt(9, carID);

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
    public boolean deleteCar(int carID) throws MySQLIntegrityConstraintViolationException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "DELETE FROM cars WHERE carID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, carID);

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
    public boolean changeCarStatus(int carID, boolean available) {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        String query = "UPDATE cars SET available=? WHERE carID=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setBoolean(1, available);
            statement.setInt(2, carID);

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
    public ArrayList<Car> getAllCars() {
        ArrayList<Car> result = new ArrayList<>();
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM cars, car_categories WHERE cars.categoryID = car_categories.categoryID AND available = TRUE";

        try {
            statement = conn.prepareStatement(query);

            if (statement.execute(query)) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    int carID = rs.getInt("carID");
                    String brand = rs.getString("brand");
                    String model = rs.getString("model");
                    int year = rs.getInt("manufacture_year");
                    String plate = rs.getString("plate");
                    int categoryID = rs.getInt("categoryID");
                    String categoryName = rs.getString("categoryName");
                    int doors = rs.getInt("doors");
                    int passengers = rs.getInt("passengers");
                    String description = rs.getString("description");
                    int mileage = rs.getInt("mileage");
                    int currentAgencyID = rs.getInt("currentAgencyID");

                    Category category = new Category(categoryID, categoryName);
                    Agency currentAgency = new Agency(currentAgencyID);
                    Car car = new Car(carID, brand, model, year, plate, category, doors, passengers, description, mileage, currentAgency);

                    result.add(car);
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
    public ArrayList<Car> getAllCars(int agencyID) {
        ArrayList<Car> result = new ArrayList<>();
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM cars, car_categories WHERE cars.categoryID = car_categories.categoryID AND currentAgencyID = ? AND available = TRUE";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, agencyID);

            if (statement.execute(query)) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    int carID = rs.getInt("carID");
                    String brand = rs.getString("brand");
                    String model = rs.getString("model");
                    int year = rs.getInt("manufacture_year");
                    String plate = rs.getString("plate");
                    int categoryID = rs.getInt("categoryID");
                    String categoryName = rs.getString("categoryName");
                    int doors = rs.getInt("doors");
                    int passengers = rs.getInt("passengers");
                    String description = rs.getString("description");
                    int mileage = rs.getInt("mileage");

                    Category category = new Category(categoryID, categoryName);
                    Agency currentAgency = new Agency(agencyID);
                    Car car = new Car(carID, brand, model, year, plate, category, doors, passengers, description, mileage, currentAgency);

                    result.add(car);
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
    public ArrayList<Car> getAllAvailableCars(int agencyID, Date fromDate, Date toDate) {
        //TODO method
        return null;
    }

    @Override
    public boolean addCategory(String categoryName, String description) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = MySQLDAOFactory.connect();
        PreparedStatement statement = null;

        String query = "INSERT INTO car_categories (categoryName, categoryDescription) "
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
        String query = "UPDATE car_categories SET categoryName=?, categoryDescription=? WHERE categoryID=?";

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
        String query = "DELETE FROM car_categories WHERE categoryID=?";

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
        String query = "SELECT * FROM car_categories";

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
