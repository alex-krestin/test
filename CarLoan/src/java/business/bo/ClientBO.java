package business.bo;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.DAOFactory;
import dao.entity.ClientDAO;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Client;
import entity.Response;
import entity.TransferObject;
import utility.SessionData;

import java.util.List;


public class ClientBO {
    private ClientDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public ClientBO() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getClientDAO();
    }

    public Response addClient(Client client) throws DuplicateEntryException {

        boolean result = dao.addClient(client.getName(), client.getSurname(), client.getSex(), client.getFiscalCode(),
                client.getBirthday(), client.getPhoneNumber(), client.getComment());

        return new Response(result);
    }

    public Response updateClient(Client client) throws DuplicateEntryException {

        boolean result = dao.updateClient(client.getId(), client.getName(), client.getSurname(), client.getSex(),
                client.getFiscalCode(), client.getBirthday(), client.getPhoneNumber(), client.getComment());

        return new Response(result);
    }

    public Response deleteClient(Client client) throws MySQLIntegrityConstraintViolationException {

        boolean result = dao.deleteClient(client.getId());

        return new Response(result);
    }

    public Response getAllClients() {

        List<Client> clients = dao.getAllClients();

        return new Response(clients);
    }

    public Response searchClient(Client client) {

        TransferObject to = dao.findClientByFiscalCode(client.getFiscalCode());

        return new Response(to);
    }
}
