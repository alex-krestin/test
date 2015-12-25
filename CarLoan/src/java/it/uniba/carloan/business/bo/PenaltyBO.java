package it.uniba.carloan.business.bo;


import it.uniba.carloan.dao.DAOFactory;
import it.uniba.carloan.dao.entity.PenaltyDAO;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Penalty;

import java.util.List;

public class PenaltyBO {

    private PenaltyDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public PenaltyBO() throws PersistenceException {
        this.dao = DAOFactory.getDAOFactory().getPenaltyDAO();
    }

    public Integer addPenalty(Penalty penalty) throws DuplicateEntryException, PersistenceException {
        return dao.add(penalty);
    }

    public boolean updatePenalty(Penalty penalty) throws DuplicateEntryException, PersistenceException {
        return dao.update(penalty);
    }

    public boolean deletePenalty(Penalty penalty) throws IntegrityConstraintViolationException, PersistenceException {
        return dao.delete(penalty);
    }

    public List<Penalty> getAllPenalties() throws PersistenceException {
        return dao.findAll();
    }

    public boolean updatePenaltyAvailability(Penalty penalty) throws PersistenceException {
        return dao.updateAvailability(penalty);
    }
}
