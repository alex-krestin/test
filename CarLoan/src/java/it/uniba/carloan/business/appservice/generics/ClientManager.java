package it.uniba.carloan.business.appservice.generics;


import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.exception.RecordNotFoundException;
import it.uniba.carloan.entity.Client;
import it.uniba.carloan.entity.Response;

public interface ClientManager {
    Response addClient(Client client) throws DuplicateEntryException, PersistenceException;
    Response updateClient(Client client) throws DuplicateEntryException, PersistenceException;
    Response deleteClient(Client client) throws IntegrityConstraintViolationException, PersistenceException;
    Response getAllClients() throws PersistenceException;
    Response searchClient(Client client) throws RecordNotFoundException, PersistenceException;
}
