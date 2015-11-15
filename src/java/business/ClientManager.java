package business;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.DAOFactory;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import dao.entity.ClientDAO;
import entity.Client;
import entity.TransferObject;
import utility.SessionData;

import java.sql.Date;
import java.util.ArrayList;


public class ClientManager {
    private ClientDAO dao;

    public ClientManager() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getClientDAO();
    }

    public TransferObject addClient(Client client) throws DuplicateEntryException {
        boolean result;

        Date bdate = new Date(client.getBirthday().getTime());

        result = dao.addClient(client.getName(), client.getSurname(), client.getSex(), client.getFiscalCode(),
                bdate, client.getPhoneNumber(), client.getComment());

        return new TransferObject(result);
    }

    public TransferObject updateClient(Client client) throws DuplicateEntryException {
        boolean result;

        // Convert java.util.Date to java.sql.Date
        Date bdate = new Date(client.getBirthday().getTime());

        result = dao.updateClient(client.getId(), client.getName(), client.getSurname(), client.getSex(),
                client.getFiscalCode(), bdate, client.getPhoneNumber(), client.getComment());

        return new TransferObject(result);
    }

    public TransferObject deleteClient(Client client) throws MySQLIntegrityConstraintViolationException {
        boolean result;
        result = dao.deleteClient(client.getId());
        return new TransferObject(result);
    }

    public TransferObject getAllClients() {
        ArrayList<Client> clients = dao.getAllClients();
        return new TransferObject(clients);
    }

    public TransferObject searchClient(Client client) {
        return dao.searchClient(client.getFiscalCode());
    }
}
