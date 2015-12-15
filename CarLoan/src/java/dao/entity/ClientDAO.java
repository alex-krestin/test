package dao.entity;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import entity.Client;

import java.time.LocalDate;
import java.util.List;


public interface ClientDAO {
    boolean addClient(String name, String surname, String sex, String fiscalCode, LocalDate birthday, String phoneNumber, String comment) throws DuplicateEntryException;
    boolean updateClient(int clientID, String name, String surname, String sex, String fiscalCode, LocalDate birthday, String phoneNumber, String comment) throws DuplicateEntryException;
    boolean deleteClient(int clientID) throws MySQLIntegrityConstraintViolationException;
    List<Client> getAllClients();
    Client findClientByFiscalCode(String fiscalCode);
}
