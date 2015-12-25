package it.uniba.carloan.dao.mysql;

import it.uniba.carloan.dao.entity.PenaltyTariffDAO;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.mysql.utility.MySOLDateConverter;
import it.uniba.carloan.entity.Category;
import it.uniba.carloan.entity.Penalty;
import it.uniba.carloan.entity.PenaltyTariff;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MySQLPenaltyTariffDAO extends MySQLAbstractDAO<PenaltyTariff, Integer> implements PenaltyTariffDAO {

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO penalty_pricelist (penaltyID, price, fromDate, toDate) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE penalty_pricelist SET penaltyID=?, price=?, fromDate=?, toDate=? WHERE id=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM penalty_pricelist WHERE id=?";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM penalties " +
                "INNER JOIN penalty_categories ON penalties.categoryID = penalty_categories.categoryID " +
                "LEFT JOIN penalty_pricelist ON penalties.penaltyID = penalty_pricelist.penaltyID " +
                "WHERE available IS TRUE ORDER BY title, fromDate";
    }

    @Override
    protected String getFindByPKQuery() {
        return "SELECT * FROM penalties " +
                "INNER JOIN penalty_categories ON penalties.categoryID = penalty_categories.categoryID " +
                "LEFT JOIN penalty_pricelist ON penalties.penaltyID = penalty_pricelist.penaltyID " +
                "WHERE id=?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, PenaltyTariff object) throws PersistenceException {
        try {
            statement.setInt(1, object.getPenaltyID());
            statement.setBigDecimal(2, object.getPrice());
            statement.setDate(3, Date.valueOf(object.getFromDate()));
            statement.setDate(4, Date.valueOf(object.getToDate()));
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, PenaltyTariff object) throws PersistenceException {
        try {
            statement.setInt(1, object.getPenaltyID());
            statement.setBigDecimal(2, object.getPrice());
            statement.setDate(3, Date.valueOf(object.getFromDate()));
            statement.setDate(4, Date.valueOf(object.getToDate()));
            statement.setInt(5, object.getId());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @SuppressWarnings("LawOfDemeter")
    @Override
    protected List<PenaltyTariff> parseResultSet(ResultSet rs) throws PersistenceException {
        List<PenaltyTariff> result = new ArrayList<>();

        if (rs != null) {
            Category category;
            Penalty penalty;
            PenaltyTariff tariff;

            try {
                while (rs.next()) {
                    category = new Category.Builder(rs.getString("categoryName"))
                            .id(rs.getInt("categoryID")).build();

                    penalty = new Penalty.Builder()
                            .id(rs.getInt("penaltyID"))
                            .title(rs.getString("title"))
                            .description(rs.getString("description"))
                            .category(category).build();

                    LocalDate fromDate = MySOLDateConverter.convertToLocalDate(rs.getDate("fromDate"));
                    LocalDate toDate = MySOLDateConverter.convertToLocalDate(rs.getDate("toDate"));

                    tariff = new PenaltyTariff.Builder()
                            .tariffID(rs.getInt("id"))
                            .penalty(penalty)
                            .price(rs.getBigDecimal("price"))
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
    public List<PenaltyTariff> findByPenaltyId(PenaltyTariff object) throws PersistenceException {
        String query = "SELECT * FROM penalties INNER JOIN penalty_categories ON " +
                "penalties.categoryID = penalty_categories.categoryID LEFT JOIN penalty_pricelist ON " +
                "penalties.penaltyID = penalty_pricelist.penaltyID WHERE fromDate IS NOT NULL " +
                "AND penalties.penaltyID = ?";

        return findByKey(query, object.getPenaltyID());
    }
}
