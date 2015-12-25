package it.uniba.carloan.dao.mysql;


import it.uniba.carloan.dao.entity.AccessoryTariffDAO;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.mysql.utility.MySOLDateConverter;
import it.uniba.carloan.entity.AccessoryTariff;
import it.uniba.carloan.entity.Category;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySQLAccessoryTariffDAO extends MySQLAbstractDAO<AccessoryTariff, Integer> implements AccessoryTariffDAO {

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO accessory_pricelist (categoryID, dailyPrice, maxDays, fromDate, toDate) "
                + " VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE accessory_pricelist SET categoryID=?, dailyPrice=?, maxDays=?, fromDate=?, toDate=? WHERE id=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM accessory_pricelist WHERE id=?";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM accessory_categories LEFT JOIN accessory_pricelist ON " +
                "accessory_categories.categoryID = accessory_pricelist.categoryID ORDER BY categoryName, fromDate";
    }

    @Override
    protected String getFindByPKQuery() {
        return "SELECT * FROM accessory_categories LEFT JOIN accessory_pricelist ON " +
                "accessory_categories.categoryID = accessory_pricelist.categoryID WHERE id=?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, AccessoryTariff object) throws PersistenceException {
        try {
            statement.setInt(1, object.getCategoryID());
            statement.setBigDecimal(2, object.getDailyPrice());
            statement.setInt(3, object.getMaxDays());
            statement.setDate(4, Date.valueOf(object.getFromDate()));
            statement.setDate(5, Date.valueOf(object.getToDate()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, AccessoryTariff object) throws PersistenceException {
        try {
            statement.setInt(1, object.getCategoryID());
            statement.setBigDecimal(2, object.getDailyPrice());
            statement.setInt(3, object.getMaxDays());
            statement.setDate(4, Date.valueOf(object.getFromDate()));
            statement.setDate(5, Date.valueOf(object.getToDate()));
            statement.setInt(6, object.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<AccessoryTariff> parseResultSet(ResultSet rs) throws PersistenceException {
        List<AccessoryTariff> result = new ArrayList<>();

        if (rs != null) {
            Category category;
            AccessoryTariff tariff;

            try {
                while (rs.next()) {
                    category = new Category.Builder(rs.getString("categoryName"))
                            .id(rs.getInt("categoryID")).build();

                    LocalDate fromDate = MySOLDateConverter.convertToLocalDate(rs.getDate("fromDate"));
                    LocalDate toDate = MySOLDateConverter.convertToLocalDate(rs.getDate("toDate"));

                    tariff = new AccessoryTariff.Builder()
                            .tariffID(rs.getInt("id"))
                            .dailyPrice(rs.getBigDecimal("dailyPrice"))
                            .maxDays(rs.getInt("maxDays"))
                            .category(category)
                            .fromDate(fromDate)
                            .toDate(toDate).build();

                    result.add(tariff);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public List<AccessoryTariff> findByCategory(AccessoryTariff object) throws PersistenceException {
        String query = "SELECT * FROM accessory_categories LEFT JOIN accessory_pricelist " +
                "ON accessory_categories.categoryID = accessory_pricelist.categoryID " +
                "WHERE fromDate IS NOT NULL AND accessory_categories.categoryID = ?";

        return findByKey(query, object.getCategoryID());
    }

}
