package it.uniba.carloan.business.appservice.mysql;

import it.uniba.carloan.business.appservice.generics.PenaltyManager;
import it.uniba.carloan.business.bo.PenaltyBO;
import it.uniba.carloan.business.bo.PenaltyCategoryBO;
import it.uniba.carloan.business.bo.PenaltyTariffBO;
import it.uniba.carloan.business.utility.TariffDateValidator;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Category;
import it.uniba.carloan.entity.Penalty;
import it.uniba.carloan.entity.PenaltyTariff;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.presentation.helper.exception.InvalidTariffDateException;

import java.util.List;

import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.closeConnection;
import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.getConnection;


public class MySQLPenaltyManager implements PenaltyManager {
    private PenaltyBO penaltyBO;
    private PenaltyCategoryBO penaltyCategoryBO;
    private PenaltyTariffBO penaltyTariffBO;

    public MySQLPenaltyManager() throws PersistenceException {
        getConnection();
        this.penaltyBO = new PenaltyBO();
        this.penaltyCategoryBO = new PenaltyCategoryBO();
        this.penaltyTariffBO = new PenaltyTariffBO();
    }

    public Response addPenalty(Penalty penalty) throws DuplicateEntryException, PersistenceException {
        penaltyBO.addPenalty(penalty);
        closeConnection();

        return new Response(true);
    }

    public Response updatePenalty(Penalty penalty) throws DuplicateEntryException, PersistenceException {
        boolean result = penaltyBO.updatePenalty(penalty);
        closeConnection();

        return new Response(result);
    }

    public Response deletePenalty(Penalty penalty) throws IntegrityConstraintViolationException, PersistenceException {
        boolean result = penaltyBO.deletePenalty(penalty);
        closeConnection();

        return new Response(result);
    }

    public Response getAllPenalties() throws PersistenceException {
        List<Penalty> result = penaltyBO.getAllPenalties();
        closeConnection();

        return new Response(result);
    }

    public Response changePenaltyState(Penalty penalty) throws PersistenceException {
        penalty.setAvailable(!penalty.isAvailable());
        boolean result = penaltyBO.updatePenaltyAvailability(penalty);
        closeConnection();

        return new Response(result);
    }

    public Response addCategory(Category category) throws DuplicateEntryException, PersistenceException {
        penaltyCategoryBO.addCategory(category);
        closeConnection();

        return new Response(true);
    }

    public Response updateCategory(Category category) throws DuplicateEntryException, PersistenceException {
        boolean result = penaltyCategoryBO.updateCategory(category);
        closeConnection();

        return new Response(result);
    }

    public Response deleteCategory(Category category) throws IntegrityConstraintViolationException, PersistenceException {
        boolean result = penaltyCategoryBO.deleteCategory(category);
        closeConnection();

        return new Response(result);
    }

    public Response getAllCategories() throws PersistenceException {
        List<Category> result = penaltyCategoryBO.getAllCategories();
        closeConnection();

        return new Response(result);
    }

    public Response addTariff(PenaltyTariff tariff) throws InvalidTariffDateException, DuplicateEntryException, PersistenceException {
        validateTariffDate(tariff);
        penaltyTariffBO.addTariff(tariff);
        closeConnection();

        return new Response(true);
    }

    public Response updateTariff(PenaltyTariff tariff) throws InvalidTariffDateException, DuplicateEntryException, PersistenceException {
        validateTariffDate(tariff);
        boolean result = penaltyTariffBO.updateTariff(tariff);
        closeConnection();

        return new Response(result);
    }

    public Response deleteTariff(PenaltyTariff tariff) throws IntegrityConstraintViolationException, PersistenceException {
        boolean result = penaltyTariffBO.deleteTariff(tariff);
        closeConnection();

        return new Response(result);
    }

    public Response getAllTariffs() throws PersistenceException {
        List<PenaltyTariff> result = penaltyTariffBO.getAllTariffs();
        closeConnection();

        return new Response(result);
    }

    private void validateTariffDate(PenaltyTariff tariff) throws PersistenceException, InvalidTariffDateException {
        List<PenaltyTariff> checkList = penaltyTariffBO.getTariffsByPenaltyId(tariff);
        closeConnection();

        TariffDateValidator<PenaltyTariff> validator = new TariffDateValidator<>();
        validator.validate(tariff, checkList);
    }

}
