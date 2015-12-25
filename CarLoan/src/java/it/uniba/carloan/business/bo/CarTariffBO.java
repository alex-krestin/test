package it.uniba.carloan.business.bo;


import it.uniba.carloan.dao.DAOFactory;
import it.uniba.carloan.dao.entity.CarTariffDAO;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.CarTariff;

import java.util.List;

public class CarTariffBO {
    private CarTariffDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public CarTariffBO() throws PersistenceException {
        this.dao = DAOFactory.getDAOFactory().getCarTariffDAO();
    }

    public Integer addTariff(CarTariff tariff) throws DuplicateEntryException, PersistenceException {
        return dao.add(tariff);
    }

    public boolean updateTariff(CarTariff tariff) throws DuplicateEntryException, PersistenceException {
        return dao.update(tariff);
    }

    public boolean deleteTariff(CarTariff tariff) throws IntegrityConstraintViolationException, PersistenceException {
        return dao.delete(tariff);
    }

    public List<CarTariff> getAllTariffs() throws PersistenceException {
        return dao.findAll();
    }

    public List<CarTariff> getTariffsByCategory(CarTariff tariff) throws PersistenceException {
        return dao.findByCategory(tariff);
    }
}
