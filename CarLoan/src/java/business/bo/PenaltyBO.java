package business.bo;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.DAOFactory;
import dao.entity.PenaltyDAO;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.*;
import presentation.helper.exception.InvalidTariffDateException;
import utility.SessionData;

import java.util.List;

public class PenaltyBO {

    private PenaltyDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public PenaltyBO() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getPenaltyDAO();
    }

    public Response getAllPenalties() {
        List<Penalty> penalties = dao.findAllPenalties();
        return new Response(penalties);
    }

    public Response addPenalty(Penalty penalty) throws DuplicateEntryException {

        boolean result = dao.addPenalty(penalty.getCategoryID(), penalty.getTitle(), penalty.getDescription());

        return new Response(result);
    }

    public Response updatePenalty(Penalty penalty) throws DuplicateEntryException {

        boolean result = dao.updatePenalty(penalty.getId(), penalty.getCategoryID(), penalty.getTitle(), penalty.getDescription());

        return new Response(result);
    }

    public Response deletePenalty(Penalty penalty) throws MySQLIntegrityConstraintViolationException {

        boolean result = dao.deletePenalty(penalty.getId());

        return new Response(result);
    }

    public Response changePenaltyState(Penalty penalty) {

        boolean result = dao.updatePenaltyStatus(penalty.getId(), penalty.isAvailable());
        return new Response(result);
    }

    public Response getAllCategories() {
        List<Category> categories = dao.findAllCategories();
        return new Response(categories);
    }

    public Response addCategory(Category category) throws DuplicateEntryException {

        boolean result = dao.addCategory(category.getName(), category.getDescription());

        return new Response(result);
    }

    public Response updateCategory(Category category) throws DuplicateEntryException {

        boolean result = dao.updateCategory(category.getId(), category.getName(), category.getDescription());

        return new Response(result);
    }

    public Response deleteCategory(Category category) throws MySQLIntegrityConstraintViolationException {

        boolean result = dao.deleteCategory(category.getId());

        return new Response(result);
    }

    public Response addTariff(PenaltyTariff tariff) throws InvalidTariffDateException {

        boolean result = dao.addPenaltyTariff(tariff.getPenaltyID(), tariff.getPrice(), tariff.getFromDate(), tariff.getToDate());

        return new Response(result);
    }

    public Response updateTariff(PenaltyTariff tariff) throws InvalidTariffDateException {

        boolean result = dao.updatePenaltyTariff(tariff.getTariffID(), tariff.getPenaltyID(),
                tariff.getPrice(), tariff.getFromDate(), tariff.getToDate());

        return new Response(result);
    }

    public Response deleteTariff(Tariff tariff) throws MySQLIntegrityConstraintViolationException {
        boolean result = dao.deletePenaltyTariff(tariff.getTariffID());

        return new Response(result);
    }

    public Response getAllTariffs() {
        List<PenaltyTariff> tariffs = dao.findPenaltyTariffs();
        return new Response(tariffs);
    }

    public List<PenaltyTariff> getAllTariffsByID(PenaltyTariff tariff) {
        return dao.findPenaltyTariffsByID(tariff.getPenaltyID());
    }
}
