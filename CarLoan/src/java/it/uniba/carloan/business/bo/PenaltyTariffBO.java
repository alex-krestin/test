package it.uniba.carloan.business.bo;


import it.uniba.carloan.dao.DAOFactory;
import it.uniba.carloan.dao.entity.PenaltyTariffDAO;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.PenaltyTariff;

import java.util.List;

public class PenaltyTariffBO {
    private PenaltyTariffDAO dao;

    public PenaltyTariffBO() throws PersistenceException {
        this.dao = DAOFactory.getDAOFactory().getPenaltyTariffDAO();
    }

    public Integer addTariff(PenaltyTariff tariff) throws DuplicateEntryException, PersistenceException {
        return dao.add(tariff);
    }

    public boolean updateTariff(PenaltyTariff tariff) throws DuplicateEntryException, PersistenceException {
        return dao.update(tariff);
    }

    public boolean deleteTariff(PenaltyTariff tariff) throws IntegrityConstraintViolationException, PersistenceException {
        return dao.delete(tariff);
    }

    public List<PenaltyTariff> getAllTariffs() throws PersistenceException {
        return dao.findAll();
    }

    public List<PenaltyTariff> getTariffsByPenaltyId(PenaltyTariff tariff) throws PersistenceException {
        return dao.findByPenaltyId(tariff);
    }
}
