package business.appservice;

import business.DateChecker;
import business.bo.CarBO;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.*;
import presentation.helper.exception.InvalidTariffDateException;

import java.time.LocalDate;
import java.util.List;


public class CarManager {
    private CarBO bo;

    public CarManager() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.bo = new CarBO();
    }

    public Response getAllCars() {
        return bo.getAllCars();
    }

    public Response getAllCars(Agency agency) {
        return bo.getAllCars(agency);
    }

    public Response addCar(Car car) throws DuplicateEntryException {
        return bo.addCar(car);
    }

    public Response updateCar(Car car) throws DuplicateEntryException {
        return bo.updateCar(car);
    }

    public Response deleteCar(Car car) throws MySQLIntegrityConstraintViolationException {
        return bo.deleteCar(car);
    }

    public Response changeCarState(Car car) {

        if(car.isAvailable()) {
            car.setAvailable(false);
        }
        else {
            car.setAvailable(true);
        }

        return bo.changeCarState(car);
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

    public Response addTariff(CarTariff tariff) throws DuplicateEntryException, InvalidTariffDateException {

        if (isInvalidTariffDate(tariff)) {
            throw new InvalidTariffDateException();
        }

        return bo.addTariff(tariff);
    }

    public Response updateTariff(CarTariff tariff) throws DuplicateEntryException, InvalidTariffDateException {

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

    private boolean isInvalidTariffDate(CarTariff tariff) {
        boolean overlap = false;
        List<CarTariff> tariffs = bo.getAllTariffsByCategory(tariff);

        LocalDate testFromDate = tariff.getFromDate();
        LocalDate testToDate = tariff.getToDate();

        for (Tariff record: tariffs) {
            overlap = DateChecker.checkDates(record.getFromDate(), record.getToDate(), testFromDate, testToDate);
            if(overlap) break;
        }

        return overlap;
    }
}
