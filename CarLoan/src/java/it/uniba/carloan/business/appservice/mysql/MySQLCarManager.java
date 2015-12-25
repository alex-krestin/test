package it.uniba.carloan.business.appservice.mysql;

import it.uniba.carloan.business.appservice.generics.CarManager;
import it.uniba.carloan.business.bo.CarBO;
import it.uniba.carloan.business.bo.CarCategoryBO;
import it.uniba.carloan.business.bo.CarTariffBO;
import it.uniba.carloan.business.utility.TariffDateValidator;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.*;
import it.uniba.carloan.presentation.helper.exception.InvalidTariffDateException;

import java.util.List;

import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.closeConnection;
import static it.uniba.carloan.dao.mysql.connector.MySQLDatabaseConnector.getConnection;


public class MySQLCarManager implements CarManager{
    private CarBO carBO;
    private CarCategoryBO carCategoryBO;
    private CarTariffBO carTariffBO;

    public MySQLCarManager() throws PersistenceException {
        getConnection();
        this.carBO = new CarBO();
        this.carCategoryBO = new CarCategoryBO();
        this.carTariffBO = new CarTariffBO();
    }

    public Response addCar(Car car) throws DuplicateEntryException, PersistenceException {
        carBO.addCar(car);
        closeConnection();

        return new Response(true);
    }

    public Response updateCar(Car car) throws DuplicateEntryException, PersistenceException {
        boolean result = carBO.updateCar(car);
        closeConnection();

        return new Response(result);
    }

    public Response deleteCar(Car car) throws IntegrityConstraintViolationException, PersistenceException {
        boolean result = carBO.deleteCar(car);
        closeConnection();

        return new Response(result);
    }

    public Response getAllCars() throws PersistenceException {
        List<Car> result = carBO.getAllCars();
        closeConnection();

        return new Response(result);
    }

    public Response getCarsByAgency(Agency agency) throws PersistenceException {
        List<Car> result = carBO.getCarsByAgency(agency);
        closeConnection();

        return new Response(result);
    }

    public Response changeCarAvailability(Car car) throws PersistenceException {
        car.setAvailable(!car.isAvailable());
        boolean result = carBO.updateCarAvailability(car);

        return new Response(result);
    }

    public Response getAllCategories() throws PersistenceException {
        List<Category> result = carCategoryBO.getAllCategories();
        closeConnection();

        return new Response(result);
    }

    public Response addCategory(Category category) throws DuplicateEntryException, PersistenceException {
        carCategoryBO.addCategory(category);
        closeConnection();

        return new Response(true);
    }

    public Response updateCategory(Category category) throws DuplicateEntryException, PersistenceException {
        boolean result = carCategoryBO.updateCategory(category);
        closeConnection();

        return new Response(result);
    }

    public Response deleteCategory(Category category) throws IntegrityConstraintViolationException, PersistenceException {
        boolean result = carCategoryBO.deleteCategory(category);
        closeConnection();

        return new Response(result);
    }

    public Response addTariff(CarTariff tariff) throws DuplicateEntryException, InvalidTariffDateException, PersistenceException {
        validateTariffDate(tariff);
        carTariffBO.addTariff(tariff);
        closeConnection();

        return new Response(true);
    }

    public Response updateTariff(CarTariff tariff) throws DuplicateEntryException, InvalidTariffDateException, PersistenceException {
        validateTariffDate(tariff);
        boolean result = carTariffBO.updateTariff(tariff);
        closeConnection();

        return new Response(result);
    }

    public Response deleteTariff(CarTariff tariff) throws IntegrityConstraintViolationException, PersistenceException {
        boolean result = carTariffBO.deleteTariff(tariff);
        closeConnection();

        return new Response(result);
    }

    public Response getAllTariffs() throws PersistenceException {
        List<CarTariff> result = carTariffBO.getAllTariffs();
        closeConnection();

        return new Response(result);
    }

    private void validateTariffDate(CarTariff tariff) throws PersistenceException, InvalidTariffDateException {
        List<CarTariff> checkList = carTariffBO.getTariffsByCategory(tariff);
        closeConnection();

        TariffDateValidator<CarTariff> validator = new TariffDateValidator<>();
        validator.validate(tariff, checkList);
    }
}
