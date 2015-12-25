package it.uniba.carloan.business.bo;


import it.uniba.carloan.dao.DAOFactory;
import it.uniba.carloan.dao.entity.CarDAO;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Agency;
import it.uniba.carloan.entity.Car;

import java.util.List;

public class CarBO {
    private CarDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public CarBO() throws PersistenceException {
        this.dao = DAOFactory.getDAOFactory().getCarDAO();
    }

    public Integer addCar(Car car) throws DuplicateEntryException, PersistenceException {
        return dao.add(car);
    }

    public boolean updateCar(Car car) throws DuplicateEntryException, PersistenceException {
        return dao.update(car);
    }

    public boolean deleteCar(Car car) throws IntegrityConstraintViolationException, PersistenceException {
        return dao.delete(car);
    }

    public List<Car> getAllCars() throws PersistenceException {
        return dao.findAll();
    }

    public List<Car> getCarsByAgency(Agency agency) throws PersistenceException {
        return dao.findByAgency(agency);
    }

    public boolean updateCarAvailability(Car car) throws PersistenceException {
        return dao.updateAvailability(car);
    }

    /*
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

    public Response addTariff(CarTariff tariff) throws DuplicateEntryException, InvalidTariffDateException {

        boolean result = dao.addCarTariff(tariff.getCategoryID(), tariff.getDailyPrice(),
                tariff.getWeeklyPrice(), tariff.getMileagePrice(), tariff.getFreeMileagePrice(), tariff.getFromDate(), tariff.getToDate());

        return new Response(result);
    }

    public Response updateTariff(CarTariff tariff) throws DuplicateEntryException, InvalidTariffDateException {

        boolean result = dao.updateCarTariff(tariff.getId(), tariff.getCategoryID(), tariff.getDailyPrice(),
                tariff.getWeeklyPrice(), tariff.getMileagePrice(), tariff.getFreeMileagePrice(), tariff.getFromDate(), tariff.getToDate());

        return new Response(result);
    }

    public Response deleteTariff(Tariff tariff) throws MySQLIntegrityConstraintViolationException {

        boolean result = dao.deleteCarTariff(tariff.getId());

        return new Response(result);
    }

    public Response getAllTariffs() {

        List<CarTariff> tariffs = dao.findCarTariffs();

        return new Response(tariffs);
    }

    public List<CarTariff> getAllTariffsByCategory(CarTariff tariff) {
        return dao.findCarTariffsByCategory(tariff.getCategoryID());
    }
    */
}
