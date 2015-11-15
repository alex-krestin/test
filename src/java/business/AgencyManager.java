package business;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.DAOFactory;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import dao.entity.AgencyDAO;
import entity.Agency;
import entity.TransferObject;
import utility.SessionData;

import java.util.ArrayList;

public class AgencyManager {
    private AgencyDAO dao;

    public AgencyManager() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getAgencyDAO();
    }

    public TransferObject getAllAgencies() {
        ArrayList<Agency> agencies = dao.getAllAgencies();
        return new TransferObject(agencies);
    }

    public TransferObject addAgency(Agency agency) throws DuplicateEntryException {
        boolean result;

        result = dao.addAgency(agency.getAgencyCode(), agency.getCity(), agency.getAddress(),
                agency.getPhoneNumber(), agency.getFaxNumber(), agency.getEmail());

        return new TransferObject(result);
    }

    public TransferObject updateAgency(Agency agency) throws DuplicateEntryException {
        boolean result;

        result = dao.updateAgency(agency.getAgencyID(), agency.getAgencyCode(), agency.getCity(),
                agency.getAddress(), agency.getPhoneNumber(), agency.getFaxNumber(), agency.getEmail());

        return new TransferObject(result);
    }

    public TransferObject deleteAgency(Agency agency) throws MySQLIntegrityConstraintViolationException {
        boolean result;

        result = dao.deleteAgency(agency.getAgencyID());

        return new TransferObject(result);
    }

    public TransferObject changeAgencyState(Agency agency) {
        boolean result;

        if(agency.isActive()) {
            agency.setActive(false);
        }
        else {
            agency.setActive(true);
        }
        result = dao.changeAgencyState(agency.getAgencyID(), agency.isActive());
        return new TransferObject(result);
    }
}