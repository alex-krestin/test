package business.bo;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.DAOFactory;
import dao.entity.AgencyDAO;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Agency;
import entity.Response;
import utility.SessionData;

import java.util.List;

public class AgencyBO {
    private AgencyDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public AgencyBO() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getAgencyDAO();
    }

    public Response getAllAgencies() {

        List<Agency> agencies = dao.findAllAgencies();

        return new Response(agencies);
    }

    public Response addAgency(Agency agency) throws DuplicateEntryException {

        boolean result = dao.addAgency(agency.getAgencyCode(), agency.getCity(), agency.getAddress(),
                agency.getPhoneNumber(), agency.getFaxNumber(), agency.getEmail());

        return new Response(result);
    }

    public Response updateAgency(Agency agency) throws DuplicateEntryException {

        boolean result = dao.updateAgency(agency.getId(), agency.getAgencyCode(), agency.getCity(),
                agency.getAddress(), agency.getPhoneNumber(), agency.getFaxNumber(), agency.getEmail());

        return new Response(result);
    }

    public Response deleteAgency(Agency agency) throws MySQLIntegrityConstraintViolationException {

        boolean result = dao.deleteAgency(agency.getId());

        return new Response(result);
    }

    public Response changeAgencyState(Agency agency) {

        boolean result = dao.updateAgencyState(agency.getId(), agency.isActive());

        return new Response(result);
    }
}
