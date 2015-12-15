package dao.entity;


import entity.ContractObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ContractDAO {
    List<ContractObject> findAvailableCarsByAgency(int agencyID, int categoryID, LocalDateTime fromDate, LocalDateTime toDate);
    List<ContractObject> findAvailableAccessoriesByAgencyAndCategory(int agencyID, int categoryID, LocalDateTime fromDate, LocalDateTime toDate);
    List<ContractObject> findAvailableServices(LocalDate date);
}
