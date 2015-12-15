package business.appservice;

import business.bo.AgencyBO;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Agency;
import entity.Response;

public class AgencyManager {
    private AgencyBO bo;

    public AgencyManager() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.bo = new AgencyBO();
    }

    public Response getAllAgencies() {
        return bo.getAllAgencies();
    }

    public Response addAgency(Agency agency) throws DuplicateEntryException {
        return bo.addAgency(agency);
    }

    public Response updateAgency(Agency agency) throws DuplicateEntryException {
        return bo.updateAgency(agency);
    }

    public Response deleteAgency(Agency agency) throws MySQLIntegrityConstraintViolationException {
        return bo.deleteAgency(agency);
    }

    public Response changeAgencyState(Agency agency) {

        if(agency.isActive()) {
            agency.setActive(false);
        }
        else {
            agency.setActive(true);
        }

        return bo.changeAgencyState(agency);
    }
}