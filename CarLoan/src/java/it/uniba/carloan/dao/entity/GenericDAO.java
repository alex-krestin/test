package it.uniba.carloan.dao.entity;


import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.exception.RecordNotFoundException;
import it.uniba.carloan.entity.Identified;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T extends Identified<PK>, PK extends Serializable> {
    Integer add(T object) throws PersistenceException, DuplicateEntryException;
    boolean update(T object) throws PersistenceException, DuplicateEntryException;
    boolean delete(T object) throws PersistenceException, IntegrityConstraintViolationException;
    List<T> findAll() throws PersistenceException;
    T findByPK(PK primaryKey) throws PersistenceException, RecordNotFoundException;
}
