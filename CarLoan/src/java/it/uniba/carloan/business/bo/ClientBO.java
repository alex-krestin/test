package it.uniba.carloan.business.bo;

import it.uniba.carloan.dao.DAOFactory;
import it.uniba.carloan.dao.entity.ClientDAO;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.exception.RecordNotFoundException;
import it.uniba.carloan.entity.Client;

import java.util.List;


public class ClientBO {
    private ClientDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public ClientBO() throws PersistenceException {
        dao = DAOFactory.getDAOFactory().getClientDAO();
    }

    public Integer addClient(Client client) throws DuplicateEntryException, PersistenceException {
        return dao.add(client);
    }

    public boolean updateClient(Client client) throws DuplicateEntryException, PersistenceException {
        return dao.update(client);
    }

    public boolean deleteClient(Client client) throws IntegrityConstraintViolationException, PersistenceException {
        return dao.delete(client);
    }

    public List<Client> getAllClients() throws PersistenceException {
        return dao.findAll();
    }

    public Client searchClient(Client client) throws RecordNotFoundException, PersistenceException {
        return dao.findByFiscalCode(client);
    }
}
