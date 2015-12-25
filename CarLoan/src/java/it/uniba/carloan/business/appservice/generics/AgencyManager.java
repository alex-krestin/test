package it.uniba.carloan.business.appservice.generics;


import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Agency;
import it.uniba.carloan.entity.Response;

public interface AgencyManager {
    Response addAgency(Agency agency) throws DuplicateEntryException, PersistenceException;
    Response updateAgency(Agency agency) throws DuplicateEntryException, PersistenceException;
    Response deleteAgency(Agency agency) throws IntegrityConstraintViolationException, PersistenceException;
    Response getAllAgencies() throws PersistenceException;
    Response changeAgencyState(Agency agency) throws PersistenceException;
}
