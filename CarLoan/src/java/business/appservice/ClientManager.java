package business.appservice;

import business.bo.ClientBO;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Client;
import entity.Response;


public class ClientManager {
    private ClientBO bo;

    public ClientManager() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.bo = new ClientBO();
    }

    public Response addClient(Client client) throws DuplicateEntryException {
        return bo.addClient(client);
    }

    public Response updateClient(Client client) throws DuplicateEntryException {
        return bo.updateClient(client);
    }

    public Response deleteClient(Client client) throws MySQLIntegrityConstraintViolationException {
        return bo.deleteClient(client);
    }

    public Response getAllClients() {
        return bo.getAllClients();
    }

    public Response searchClient(Client client) {
        return bo.searchClient(client);
    }
}
