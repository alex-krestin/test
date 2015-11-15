package business;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.DAOFactory;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import dao.entity.ServiceDAO;
import entity.Service;
import entity.TransferObject;
import utility.SessionData;

import java.util.ArrayList;


public class ServiceManager {
    private ServiceDAO dao;

    public ServiceManager() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getServiceDAO();
    }

    public TransferObject getAllServices() {
        ArrayList<Service> agencies = dao.getAllServices();
        return new TransferObject(agencies);
    }

    public TransferObject addService(Service service) throws DuplicateEntryException {
        boolean result;
        result = dao.addService(service.getTitle(), service.getDescription());
        return new TransferObject(result);
    }

    public TransferObject updateService(Service service) throws DuplicateEntryException {
        boolean result;
        result = dao.updateService(service.getId(), service.getTitle(), service.getDescription());
        return new TransferObject(result);
    }

    public TransferObject deleteService(Service service) throws MySQLIntegrityConstraintViolationException {
        boolean result;
        result = dao.deleteService(service.getId());
        return new TransferObject(result);
    }

    public TransferObject changeServiceStatus(Service service) {
        boolean result;

        if(service.isAvailable()) {
            service.setAvailable(false);
        }
        else {
            service.setAvailable(true);
        }
        result = dao.changeServiceStatus(service.getId(), service.isAvailable());
        return new TransferObject(result);
    }
}
