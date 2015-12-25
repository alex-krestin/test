package it.uniba.carloan.dao.entity;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.UnknownTableException;

import java.math.BigDecimal;
import java.util.Date;

public interface ContractAdditionDAO {
    // Addition Types: car = 0, accessory = 1, service = 2, penalty = 3

    boolean addSimpleAddition(int additionType, int referenceID, int contractID, BigDecimal price) throws UnknownTableException;
    boolean addBookableAddition(int additionType, int referenceID, int contractID, Date fromDate, Date toDate, BigDecimal price) throws UnknownTableException;
    boolean deleteAddition(int additionType, int id) throws UnknownTableException, MySQLIntegrityConstraintViolationException;
}
