package it.uniba.carloan.dao.entity;

import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.exception.RecordNotFoundException;
import it.uniba.carloan.entity.Client;


public interface ClientDAO extends GenericDAO<Client, Integer> {
    Client findByFiscalCode(Client object) throws PersistenceException, RecordNotFoundException;
}
