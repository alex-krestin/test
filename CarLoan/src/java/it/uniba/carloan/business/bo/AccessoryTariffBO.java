package it.uniba.carloan.business.bo;


import it.uniba.carloan.dao.DAOFactory;
import it.uniba.carloan.dao.entity.AccessoryTariffDAO;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.AccessoryTariff;

import java.util.List;

public class AccessoryTariffBO {
    private AccessoryTariffDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public AccessoryTariffBO() throws PersistenceException {
        this.dao = DAOFactory.getDAOFactory().getAccessoryTariffDAO();
    }

    public Integer addTariff(AccessoryTariff tariff) throws DuplicateEntryException, PersistenceException {
        return dao.add(tariff);
    }

    public boolean updateTariff(AccessoryTariff tariff) throws DuplicateEntryException, PersistenceException {
        return dao.update(tariff);
    }

    public boolean deleteTariff(AccessoryTariff tariff) throws IntegrityConstraintViolationException, PersistenceException {
        return dao.delete(tariff);
    }

    public List<AccessoryTariff> getAllTariffs() throws PersistenceException {
        return dao.findAll();
    }

    public List<AccessoryTariff> getTariffsByCategory(AccessoryTariff tariff) throws PersistenceException {
        return dao.findByCategory(tariff);
    }
}
