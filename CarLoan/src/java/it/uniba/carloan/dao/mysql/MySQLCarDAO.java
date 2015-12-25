package it.uniba.carloan.dao.mysql;


import it.uniba.carloan.dao.entity.CarDAO;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Agency;
import it.uniba.carloan.entity.Car;
import it.uniba.carloan.entity.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLCarDAO extends MySQLAbstractDAO<Car, Integer> implements CarDAO {

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO cars (brand, model, manufacture_year, plate, categoryID, doors, " +
                "passengers, description, mileage, currentAgencyID)  "
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE cars SET brand=?, model=?, manufacture_year=?, plate=?, categoryID=?, " +
                "doors=?, passengers=?, description=? WHERE carID=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM cars WHERE carID=?";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM cars, car_categories, agencies WHERE cars.categoryID = car_categories.categoryID " +
                "AND cars.currentAgencyID = agencies.agencyID AND available IS TRUE";
    }

    @Override
    protected String getFindByPKQuery() {
        return "SELECT * FROM cars, car_categories, agencies WHERE cars.categoryID = car_categories.categoryID " +
                "AND cars.currentAgencyID = agencies.agencyID AND carID=?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Car object) throws PersistenceException {
        try {
            statement.setString(1, object.getBrand());
            statement.setString(2, object.getModel());
            statement.setInt(3, object.getYear());
            statement.setString(4, object.getPlate());
            statement.setInt(5, object.getCategoryID());
            statement.setInt(6, object.getDoors());
            statement.setInt(7, object.getPassengers());
            statement.setString(8, object.getDescription());
            statement.setInt(9, object.getMileage());
            statement.setInt(10, object.getCurrentAgencyID());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Car object) throws PersistenceException {
        try {
            statement.setString(1, object.getBrand());
            statement.setString(2, object.getModel());
            statement.setInt(3, object.getYear());
            statement.setString(4, object.getPlate());
            statement.setInt(5, object.getCategoryID());
            statement.setInt(6, object.getDoors());
            statement.setInt(7, object.getPassengers());
            statement.setString(8, object.getDescription());
            statement.setInt(9, object.getId());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected List<Car> parseResultSet(ResultSet rs) throws PersistenceException {
        List<Car> result = new ArrayList<>();

        if (rs != null) {
            Category category;
            Agency agency;
            Car car;

            try {
                while (rs.next()) {
                    category = new Category.Builder(rs.getString("categoryName"))
                            .description(rs.getString("categoryDescription"))
                            .id(rs.getInt("categoryID")).build();

                    agency = new Agency.Builder()
                            .id(rs.getInt("agencyID"))
                            .agencyCode(rs.getString("agencyCode"))
                            .city(rs.getString("city"))
                            .address(rs.getString("address"))
                            .phoneNumber(rs.getString("tel"))
                            .faxNumber(rs.getString("fax"))
                            .email(rs.getString("email")).build();

                    car = new Car.Builder()
                            .carID(rs.getInt("carID"))
                            .brand(rs.getString("brand"))
                            .model(rs.getString("model"))
                            .year(rs.getInt("manufacture_year"))
                            .plate(rs.getString("plate"))
                            .doors(rs.getInt("doors"))
                            .passengers(rs.getInt("passengers"))
                            .description(rs.getString("description"))
                            .mileage(rs.getInt("mileage"))
                            .currentAgency(agency)
                            .category(category).build();

                    result.add(car);
                }
            } catch (SQLException e) {
                throw new PersistenceException(e);
            }
        }

        return result;
    }

    @Override
    public List<Car> findByAgency(Agency object) throws PersistenceException {
        String query = "SELECT * FROM cars, car_categories, agencies WHERE cars.categoryID = car_categories.categoryID " +
                "AND currentAgencyID = ? AND cars.currentAgencyID = agencies.agencyID AND available IS TRUE";

        return findByKey(query, object.getId());
    }

    @Override
    public boolean updateAvailability(Car object) throws PersistenceException {
        String query = "UPDATE cars SET available=? WHERE carID=?";
        return updateGenericField(query, object.isAvailable(), object.getId());
    }
}
