package it.uniba.carloan.business.bo;


import it.uniba.carloan.dao.DAOFactory;
import it.uniba.carloan.dao.entity.AgencyDAO;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Agency;

import java.util.List;

public class AgencyBO {
    private AgencyDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public AgencyBO() throws PersistenceException {
        this.dao = DAOFactory.getDAOFactory().getAgencyDAO();
    }


    public Integer addAgency(Agency agency) throws DuplicateEntryException, PersistenceException {
        return dao.add(agency);
    }

    public boolean updateAgency(Agency agency) throws DuplicateEntryException, PersistenceException {
        return dao.update(agency);
    }

    public boolean deleteAgency(Agency agency) throws IntegrityConstraintViolationException, PersistenceException {
        return dao.delete(agency);
    }

    public List<Agency> getAllAgencies() throws PersistenceException {
        return dao.findAll();
    }

    public boolean changeAgencyState(Agency agency) throws PersistenceException {
        return dao.updateState(agency);
    }
}
