package dao.entity;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import entity.Agency;

import java.util.ArrayList;

public interface AgencyDAO {
    boolean addAgency(String agencyCode, String city, String address, String tel, String fax, String email) throws DuplicateEntryException;
    boolean updateAgency(int agencyID, String agencyCode, String city, String address, String tel, String fax, String email) throws DuplicateEntryException;
    boolean deleteAgency(int agencyID) throws MySQLIntegrityConstraintViolationException;
    boolean changeAgencyState(int agencyID, boolean active);
    ArrayList<Agency> getAllAgencies();
}
