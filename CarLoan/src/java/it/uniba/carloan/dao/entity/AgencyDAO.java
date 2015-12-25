package it.uniba.carloan.dao.entity;

import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Agency;

public interface AgencyDAO extends GenericDAO<Agency, Integer> {
    boolean updateState(Agency object) throws PersistenceException;
}
