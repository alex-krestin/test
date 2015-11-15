package dao.entity;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import entity.Service;
import java.util.ArrayList;


public interface ServiceDAO {
    boolean addService(String title, String description) throws DuplicateEntryException;
    boolean updateService(int serviceID, String title, String description) throws DuplicateEntryException;
    boolean deleteService(int serviceID) throws MySQLIntegrityConstraintViolationException;
    boolean changeServiceStatus(int serviceID, boolean available);
    ArrayList<Service> getAllServices();
}
