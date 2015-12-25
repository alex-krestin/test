package it.uniba.carloan.dao.entity;


import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Penalty;

public interface PenaltyDAO extends GenericDAO<Penalty, Integer> {
    boolean updateAvailability(Penalty object) throws PersistenceException;
}
