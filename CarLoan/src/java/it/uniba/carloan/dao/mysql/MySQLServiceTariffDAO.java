package it.uniba.carloan.dao.mysql;


import it.uniba.carloan.dao.entity.ServiceTariffDAO;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.mysql.utility.MySOLDateConverter;
import it.uniba.carloan.entity.Service;
import it.uniba.carloan.entity.ServiceTariff;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySQLServiceTariffDAO extends MySQLAbstractDAO<ServiceTariff, Integer> implements ServiceTariffDAO {
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO service_pricelist (serviceID, price, fromDate, toDate) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE service_pricelist SET serviceID=?, price=?, fromDate=?, toDate=? WHERE id=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM service_pricelist WHERE id=?";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM services LEFT JOIN service_pricelist ON " +
                "services.serviceID = service_pricelist.serviceID WHERE " +
                "available = TRUE ORDER BY title, fromDate";
    }

    @Override
    protected String getFindByPKQuery() {
        return "SELECT * FROM services LEFT JOIN service_pricelist ON " +
                "services.serviceID = service_pricelist.serviceID " +
                "WHERE id=?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, ServiceTariff object) throws PersistenceException {
        try {
            statement.setInt(1, object.getServiceID());
            statement.setBigDecimal(2, object.getPrice());
            statement.setDate(3, Date.valueOf(object.getFromDate()));
            statement.setDate(4, Date.valueOf(object.getToDate()));
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, ServiceTariff object) throws PersistenceException {
        try {
            statement.setInt(1, object.getServiceID());
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
    protected List<ServiceTariff> parseResultSet(ResultSet rs) throws PersistenceException {
        List<ServiceTariff> result = new ArrayList<>();

        if (rs != null) {
            Service service;
            ServiceTariff tariff;

            try {
                while (rs.next()) {
                    service = new Service.Builder()
                            .id(rs.getInt("serviceID"))
                            .title(rs.getString("title"))
                            .description(rs.getString("description")).build();

                    LocalDate fromDate = MySOLDateConverter.convertToLocalDate(rs.getDate("fromDate"));
                    LocalDate toDate = MySOLDateConverter.convertToLocalDate(rs.getDate("toDate"));

                    tariff = new ServiceTariff.Builder()
                            .tariffID(rs.getInt("id"))
                            .service(service)
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
    public List<ServiceTariff> findByServiceId(ServiceTariff object) throws PersistenceException {
        String query = "SELECT * FROM services LEFT JOIN service_pricelist ON " +
                "services.serviceID = service_pricelist.serviceID WHERE fromDate IS NOT NULL " +
                "AND services.serviceID = ?";
        return findByKey(query, object.getServiceID());
    }


}
