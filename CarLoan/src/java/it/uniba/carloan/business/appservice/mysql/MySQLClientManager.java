package it.uniba.carloan.business.appservice.mysql;

import it.uniba.carloan.business.appservice.generics.ClientManager;
import it.uniba.carloan.business.bo.ClientBO;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.exception.RecordNotFoundException;
import it.uniba.carloan.entity.Client;
import it.uniba.carloan.entity.Response;

import java.util.List;

import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.closeConnection;
import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.getConnection;


public class MySQLClientManager implements ClientManager {
    private ClientBO bo;

    public MySQLClientManager() throws PersistenceException {
        getConnection();
        this.bo = new ClientBO();
    }

    public Response addClient(Client client) throws DuplicateEntryException, PersistenceException {
        bo.addClient(client);
        closeConnection();

        return new Response(true);
    }

    public Response updateClient(Client client) throws DuplicateEntryException, PersistenceException {
        boolean result = bo.updateClient(client);
        closeConnection();

        return new Response(result);
    }

    public Response deleteClient(Client client) throws IntegrityConstraintViolationException, PersistenceException {
        boolean result = bo.deleteClient(client);
        closeConnection();

        return new Response(result);
    }

    public Response getAllClients() throws PersistenceException {
        List<Client> result = bo.getAllClients();
        closeConnection();

        return new Response(result);
    }

    public Response searchClient(Client client) throws RecordNotFoundException, PersistenceException {
        Client result = bo.searchClient(client);
        closeConnection();

        return new Response(result);
    }
}
