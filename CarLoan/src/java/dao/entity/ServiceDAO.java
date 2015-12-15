package dao.entity;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import entity.Service;
import entity.ServiceTariff;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public interface ServiceDAO {
    boolean addService(String title, String description) throws DuplicateEntryException;
    boolean updateService(int serviceID, String title, String description) throws DuplicateEntryException;
    boolean deleteService(int serviceID) throws MySQLIntegrityConstraintViolationException;
    boolean updateServiceStatus(int serviceID, boolean available);
    List<Service> findAllServices();
    boolean addServiceTariff(int penaltyID, BigDecimal fixedPrice, LocalDate fromDate, LocalDate toDate);
    boolean updateServiceTariff(int tariffID, int penaltyID, BigDecimal fixedPrice, LocalDate fromDate, LocalDate toDate);
    boolean deleteServiceTariff(int tariffID) throws MySQLIntegrityConstraintViolationException;
    List<ServiceTariff> findServiceTariffs();
    List<ServiceTariff> findServiceTariffsByID(int serviceID);
}
