package business.appservice;


import business.DateChecker;
import business.bo.ServiceBO;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.Service;
import entity.ServiceTariff;
import entity.Tariff;
import presentation.helper.exception.InvalidTariffDateException;

import java.time.LocalDate;
import java.util.List;


public class ServiceManager {
    private ServiceBO bo;

    public ServiceManager() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.bo = new ServiceBO();
    }

    public Response getAllServices() {
        return bo.getAllServices();
    }

    public Response addService(Service service) throws DuplicateEntryException {
        return bo.addService(service);
    }

    public Response updateService(Service service) throws DuplicateEntryException {
        return bo.updateService(service);
    }

    public Response deleteService(Service service) throws MySQLIntegrityConstraintViolationException {
        return bo.deleteService(service);
    }

    public Response changeServiceStatus(Service service) {

        if(service.isAvailable()) {
            service.setAvailable(false);
        }
        else {
            service.setAvailable(true);
        }

        return bo.changeServiceStatus(service);
    }

    public Response addTariff(ServiceTariff tariff) throws InvalidTariffDateException {

        if (isInvalidTariffDate(tariff)) {
            throw new InvalidTariffDateException();
        }

        return bo.addTariff(tariff);
    }

    public Response updateTariff(ServiceTariff tariff) throws InvalidTariffDateException {

        if (isInvalidTariffDate(tariff)) {
            throw new InvalidTariffDateException();
        }

        return bo.updateTariff(tariff);
    }

    public Response deleteTariff(Tariff tariff) throws MySQLIntegrityConstraintViolationException {
        return bo.deleteTariff(tariff);
    }

    public Response getAllTariffs() {
        return bo.getAllTariffs();
    }

    private boolean isInvalidTariffDate(ServiceTariff tariff) {
        boolean overlap = false;
        List<ServiceTariff> tariffs = bo.getAllTariffsByID(tariff);

        LocalDate testFromDate = tariff.getFromDate();
        LocalDate testToDate = tariff.getToDate();

        for (Tariff record: tariffs) {
            overlap = DateChecker.checkDates(record.getFromDate(), record.getToDate(), testFromDate, testToDate);
            if(overlap) break;
        }

        return overlap;
    }
}
