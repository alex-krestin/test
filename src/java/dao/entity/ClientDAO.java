package dao.entity;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import entity.Client;

import java.sql.Date;
import java.util.ArrayList;


public interface ClientDAO {
    boolean addClient(String name, String surname, String sex, String fiscalCode, Date birthday, String phoneNumber, String comment) throws DuplicateEntryException;
    boolean updateClient(int clientID, String name, String surname, String sex, String fiscalCode, Date birthday, String phoneNumber, String comment) throws DuplicateEntryException;
    boolean deleteClient(int clientID) throws MySQLIntegrityConstraintViolationException;
    ArrayList<Client> getAllClients();
    Client searchClient(String fiscalCode);
}
