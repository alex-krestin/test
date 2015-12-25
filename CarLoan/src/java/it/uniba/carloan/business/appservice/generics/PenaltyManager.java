package it.uniba.carloan.business.appservice.generics;


import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Category;
import it.uniba.carloan.entity.Penalty;
import it.uniba.carloan.entity.PenaltyTariff;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.presentation.helper.exception.InvalidTariffDateException;

public interface PenaltyManager {
    Response addPenalty(Penalty penalty) throws DuplicateEntryException, PersistenceException;
    Response updatePenalty(Penalty penalty) throws DuplicateEntryException, PersistenceException;
    Response deletePenalty(Penalty penalty) throws IntegrityConstraintViolationException, PersistenceException;
    Response getAllPenalties() throws PersistenceException;
    Response changePenaltyState(Penalty penalty) throws PersistenceException;
    Response addCategory(Category category) throws DuplicateEntryException, PersistenceException;
    Response updateCategory(Category category) throws DuplicateEntryException, PersistenceException;
    Response deleteCategory(Category category) throws IntegrityConstraintViolationException, PersistenceException;
    Response getAllCategories() throws PersistenceException;
    Response addTariff(PenaltyTariff tariff) throws InvalidTariffDateException, DuplicateEntryException, PersistenceException;
    Response updateTariff(PenaltyTariff tariff) throws InvalidTariffDateException, DuplicateEntryException, PersistenceException;
    Response deleteTariff(PenaltyTariff tariff) throws IntegrityConstraintViolationException, PersistenceException;
    Response getAllTariffs() throws PersistenceException;

}
