package dao.entity;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import entity.Agency;

import java.util.List;

public interface AgencyDAO {
    boolean addAgency(String agencyCode, String city, String address, String tel, String fax, String email) throws DuplicateEntryException;
    boolean updateAgency(int agencyID, String agencyCode, String city, String address, String tel, String fax, String email) throws DuplicateEntryException;
    boolean deleteAgency(int agencyID) throws MySQLIntegrityConstraintViolationException;
    boolean updateAgencyState(int agencyID, boolean active);
    List<Agency> findAllAgencies();
}
