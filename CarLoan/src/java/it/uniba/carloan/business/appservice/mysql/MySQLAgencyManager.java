package it.uniba.carloan.business.appservice.mysql;

import it.uniba.carloan.business.appservice.generics.AgencyManager;
import it.uniba.carloan.business.bo.AgencyBO;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Agency;
import it.uniba.carloan.entity.Response;

import java.util.List;

import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.closeConnection;
import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.getConnection;

public class MySQLAgencyManager implements AgencyManager {
    private AgencyBO bo;

    public MySQLAgencyManager() throws PersistenceException {
        getConnection();
        this.bo = new AgencyBO();
    }

    public Response addAgency(Agency agency) throws DuplicateEntryException, PersistenceException {
        bo.addAgency(agency);
        closeConnection();

        return new Response(true);
    }

    public Response updateAgency(Agency agency) throws DuplicateEntryException, PersistenceException {
        boolean result = bo.updateAgency(agency);
        closeConnection();

        return new Response(result);
    }

    public Response deleteAgency(Agency agency) throws IntegrityConstraintViolationException, PersistenceException {
        boolean result = bo.deleteAgency(agency);
        closeConnection();

        return new Response(result);
    }

    public Response getAllAgencies() throws PersistenceException {
        List<Agency> result = bo.getAllAgencies();
        closeConnection();

        return new Response(result);
    }

    public Response changeAgencyState(Agency agency) throws PersistenceException {
        agency.setActive(!agency.isActive());
        boolean result = bo.changeAgencyState(agency);
        closeConnection();

        return new Response(result);
    }
}