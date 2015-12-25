package it.uniba.carloan.dao.entity;


import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.PenaltyTariff;

import java.util.List;

public interface PenaltyTariffDAO extends GenericDAO<PenaltyTariff, Integer> {
    List<PenaltyTariff> findByPenaltyId(PenaltyTariff object) throws PersistenceException;
}
