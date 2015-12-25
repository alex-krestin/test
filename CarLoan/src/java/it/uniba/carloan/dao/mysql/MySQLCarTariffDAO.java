package it.uniba.carloan.dao.mysql;


import it.uniba.carloan.dao.entity.CarTariffDAO;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.mysql.utility.MySOLDateConverter;
import it.uniba.carloan.entity.CarTariff;
import it.uniba.carloan.entity.Category;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySQLCarTariffDAO extends MySQLAbstractDAO<CarTariff, Integer> implements CarTariffDAO {

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO car_pricelist (categoryID, dailyPrice, weeklyPrice, kmPrice, freeKmPrice, " +
                "fromDate, toDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE car_pricelist SET categoryID=?, dailyPrice=?, weeklyPrice=?, kmPrice=?, " +
                "freeKmPrice=?, fromDate=?, toDate=? WHERE id=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM car_pricelist WHERE id=?";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM car_categories LEFT JOIN car_pricelist ON " +
                "car_categories.categoryID = car_pricelist.categoryID ORDER BY categoryName, fromDate";
    }

    @Override
    protected String getFindByPKQuery() {
        return "SELECT * FROM car_categories LEFT JOIN car_pricelist ON " +
                "car_categories.categoryID = car_pricelist.categoryID WHERE id=?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, CarTariff object) throws PersistenceException {
        try {
            statement.setInt(1, object.getCategoryID());
            statement.setBigDecimal(2, object.getDailyPrice());
            statement.setBigDecimal(3, object.getWeeklyPrice());
            statement.setBigDecimal(4, object.getMileagePrice());
            statement.setBigDecimal(5, object.getFreeMileagePrice());
            statement.setDate(6, Date.valueOf(object.getFromDate()));
            statement.setDate(7, Date.valueOf(object.getToDate()));
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, CarTariff object) throws PersistenceException {
        try {
            statement.setInt(1, object.getCategoryID());
            statement.setBigDecimal(2, object.getDailyPrice());
            statement.setBigDecimal(3, object.getWeeklyPrice());
            statement.setBigDecimal(4, object.getMileagePrice());
            statement.setBigDecimal(5, object.getFreeMileagePrice());
            statement.setDate(6, Date.valueOf(object.getFromDate()));
            statement.setDate(7, Date.valueOf(object.getToDate()));
            statement.setInt(8, object.getId());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @SuppressWarnings("LawOfDemeter")
    @Override
    protected List<CarTariff> parseResultSet(ResultSet rs) throws PersistenceException {
        List<CarTariff> result = new ArrayList<>();

        if (rs != null) {
            Category category;
            CarTariff tariff;

            try {
                while (rs.next()) {
                    category = new Category.Builder(rs.getString("categoryName"))
                            .id(rs.getInt("categoryID")).build();

                    LocalDate fromDate = MySOLDateConverter.convertToLocalDate(rs.getDate("fromDate"));
                    LocalDate toDate = MySOLDateConverter.convertToLocalDate(rs.getDate("toDate"));

                    tariff = new CarTariff.Builder()
                            .tariffID(rs.getInt("id"))
                            .category(category)
                            .dailyPrice(rs.getBigDecimal("dailyPrice"))
                            .weeklyPrice(rs.getBigDecimal("weeklyPrice"))
                            .mileagePrice(rs.getBigDecimal("kmPrice"))
                            .freeMileagePrice(rs.getBigDecimal("freeKmPrice"))
                            .fromDate(fromDate)
                            .toDate(toDate).build();

                    result.add(tariff);
                }
            } catch (SQLException e) {
                throw new PersistenceException(e);
            }
        }

        return result;
    }

    @Override
    public List<CarTariff> findByCategory(CarTariff object) throws PersistenceException {
        String query = "SELECT * FROM car_categories LEFT JOIN car_pricelist " +
                "ON car_categories.categoryID = car_pricelist.categoryID " +
                "WHERE fromDate IS NOT NULL AND car_categories.categoryID = ?";

        return findByKey(query, object.getCategoryID());
    }
}
