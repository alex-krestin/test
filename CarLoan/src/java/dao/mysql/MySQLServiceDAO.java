package dao.mysql;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.entity.ServiceDAO;
import dao.exception.DuplicateEntryException;
import entity.Service;
import entity.ServiceTariff;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static dao.mysql.MySQLDAOFactory.*;


@SuppressWarnings("LawOfDemeter")
public class MySQLServiceDAO extends MySQLCommonQueries implements ServiceDAO{
    private static final Logger log = Logger.getLogger(MySQLServiceDAO.class.getName());

    @Override
    public boolean addService(String title, String description) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
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
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public boolean updateService(int serviceID, String title, String description) throws DuplicateEntryException {
        boolean result = false;
        Connection conn = connect();
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
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public boolean deleteService(int serviceID) throws MySQLIntegrityConstraintViolationException {
        return deleteRecord("services", "serviceID", serviceID);
    }

    @Override
    public boolean updateServiceStatus(int serviceID, boolean available) {
        return updateBooleanField("services", "available", "serviceID", serviceID, available);
    }

    @Override
    public List<Service> findAllServices() {
        List<Service> result = new ArrayList<>();
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM services WHERE available = TRUE";

        try {
            statement = conn.prepareStatement(query);

            if (statement.execute()) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    Service service = new Service.Builder()
                            .id(rs.getInt("serviceID"))
                            .title(rs.getString("title"))
                            .description(rs.getString("description")).build();

                    result.add(service);
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public boolean addServiceTariff(int serviceID, BigDecimal fixedPrice, LocalDate fromDate, LocalDate toDate) {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;

        String query = "INSERT INTO service_pricelist (serviceID, price, fromDate, toDate) VALUES (?, ?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, serviceID);
            statement.setBigDecimal(2, fixedPrice);
            statement.setDate(3, Date.valueOf(fromDate));
            statement.setDate(4, Date.valueOf(toDate));

            if (statement.executeUpdate() == 1)
                result = true;

        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public boolean updateServiceTariff(int tariffID, int serviceID, BigDecimal fixedPrice, LocalDate fromDate, LocalDate toDate) {
        boolean result = false;
        Connection conn = connect();
        PreparedStatement statement = null;

        String query = "UPDATE service_pricelist SET serviceID=?, price=?, fromDate=?, toDate=? WHERE id=?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, serviceID);
            statement.setBigDecimal(2, fixedPrice);
            statement.setDate(3, Date.valueOf(fromDate));
            statement.setDate(4, Date.valueOf(toDate));
            statement.setInt(5, tariffID);

            if (statement.executeUpdate() == 1)
                result = true;

        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public boolean deleteServiceTariff(int tariffID) throws MySQLIntegrityConstraintViolationException {
        return deleteRecord("service_pricelist", "id", tariffID);
    }

    @Override
    public List<ServiceTariff> findServiceTariffs() {
        List<ServiceTariff> result = new ArrayList<>();
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM services LEFT JOIN service_pricelist ON " +
                "services.serviceID = service_pricelist.serviceID WHERE available = TRUE ORDER BY title, fromDate";

        try {
            statement = conn.prepareStatement(query);

            if (statement.execute()) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    Service service = new Service.Builder()
                            .id(rs.getInt("serviceID"))
                            .title(rs.getString("title"))
                            .description(rs.getString("description")).build();

                    LocalDate fromDate = MySQLDateUtility.convertToLocalDate(rs.getDate("fromDate"));
                    LocalDate toDate = MySQLDateUtility.convertToLocalDate(rs.getDate("toDate"));

                    ServiceTariff tariff = new ServiceTariff.Builder()
                            .tariffID(rs.getInt("id"))
                            .service(service)
                            .price(rs.getBigDecimal("price"))
                            .fromDate(fromDate)
                            .toDate(toDate).build();

                    result.add(tariff);
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }

    @Override
    public List<ServiceTariff> findServiceTariffsByID(int serviceID) {
        List<ServiceTariff> result = new ArrayList<>();
        Connection conn = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM services LEFT JOIN service_pricelist ON " +
                "services.serviceID = service_pricelist.serviceID WHERE fromDate IS NOT NULL " +
                "AND services.serviceID = ?";

        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, serviceID);

            if (statement.execute()) {
                rs = statement.getResultSet();
            }

            if (rs != null) {
                while (rs.next()) {
                    Service service = new Service.Builder()
                            .id(serviceID)
                            .title(rs.getString("title"))
                            .description(rs.getString("description")).build();

                    LocalDate fromDate = MySQLDateUtility.convertToLocalDate(rs.getDate("fromDate"));
                    LocalDate toDate = MySQLDateUtility.convertToLocalDate(rs.getDate("toDate"));

                    ServiceTariff tariff = new ServiceTariff.Builder()
                            .tariffID(rs.getInt("id"))
                            .service(service)
                            .price(rs.getBigDecimal("price"))
                            .fromDate(fromDate)
                            .toDate(toDate).build();

                    result.add(tariff);
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "MySQL Error: ", e);
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(conn);
        }

        return result;
    }


}
