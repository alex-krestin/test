package business.appservice;

import business.DateChecker;
import business.bo.PenaltyBO;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.*;
import presentation.helper.exception.InvalidTariffDateException;

import java.time.LocalDate;
import java.util.List;


public class PenaltyManager {
    private PenaltyBO bo;

    public PenaltyManager() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.bo = new PenaltyBO();
    }

    public Response getAllPenalties() {
        return bo.getAllPenalties();
    }

    public Response addPenalty(Penalty penalty) throws DuplicateEntryException {
        return bo.addPenalty(penalty);
    }

    public Response updatePenalty(Penalty penalty) throws DuplicateEntryException {
        return bo.updatePenalty(penalty);
    }

    public Response deletePenalty(Penalty penalty) throws MySQLIntegrityConstraintViolationException {
        return bo.deletePenalty(penalty);
    }

    public Response changePenaltyState(Penalty penalty) {

        if(penalty.isAvailable()) {
            penalty.setAvailable(false);
        }
        else {
            penalty.setAvailable(true);
        }

        return bo.changePenaltyState(penalty);
    }

    public Response getAllCategories() {
        return bo.getAllCategories();
    }

    public Response addCategory(Category category) throws DuplicateEntryException {
        return bo.addCategory(category);
    }

    public Response updateCategory(Category category) throws DuplicateEntryException {
        return bo.updateCategory(category);
    }

    public Response deleteCategory(Category category) throws MySQLIntegrityConstraintViolationException {
        return bo.deleteCategory(category);
    }

    public Response addTariff(PenaltyTariff tariff) throws InvalidTariffDateException {

        if (isInvalidTariffDate(tariff)) {
            throw new InvalidTariffDateException();
        }

        return bo.addTariff(tariff);
    }

    public Response updateTariff(PenaltyTariff tariff) throws InvalidTariffDateException {

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

    private boolean isInvalidTariffDate(PenaltyTariff tariff) {
        boolean overlap = false;
        List<PenaltyTariff> tariffs = bo.getAllTariffsByID(tariff);

        LocalDate testFromDate = tariff.getFromDate();
        LocalDate testToDate = tariff.getToDate();

        for (Tariff record: tariffs) {
            overlap = DateChecker.checkDates(record.getFromDate(), record.getToDate(), testFromDate, testToDate);
            if(overlap) break;
        }

        return overlap;
    }

}
