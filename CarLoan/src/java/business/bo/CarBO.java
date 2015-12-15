package business.bo;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.DAOFactory;
import dao.entity.CarDAO;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.*;
import presentation.helper.exception.InvalidTariffDateException;
import utility.SessionData;

import java.util.List;

public class CarBO {
    private CarDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public CarBO() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getCarDAO();
    }

    public Response getAllCars() {
        List<Car> cars = dao.findAllCars();
        return new Response(cars);
    }

    public Response getAllCars(Agency agency) {

        List<Car> cars = dao.findCarsByAgency(agency.getId());

        return new Response(cars);
    }

    public Response addCar(Car car) throws DuplicateEntryException {

        boolean result = dao.addCar(car.getBrand(), car.getModel(), car.getYear(), car.getCategoryID(), car.getCurrentAgencyID(),
                car.getPlate(), car.getDoors(), car.getPassengers(), car.getDescription(), car.getMileage());

        return new Response(result);
    }

    public Response updateCar(Car car) throws DuplicateEntryException {

        boolean result = dao.updateCar(car.getId(), car.getBrand(), car.getModel(), car.getYear(), car.getCategoryID(),
                car.getPlate(), car.getDoors(), car.getPassengers(), car.getDescription());

        return new Response(result);
    }

    public Response deleteCar(Car car) throws MySQLIntegrityConstraintViolationException {

        boolean result = dao.deleteCar(car.getId());

        return new Response(result);
    }

    public Response changeCarState(Car car) {

        boolean result = dao.updateCarStatus(car.getId(), car.isAvailable());

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

    public Response addTariff(CarTariff tariff) throws DuplicateEntryException, InvalidTariffDateException {

        boolean result = dao.addCarTariff(tariff.getCategoryID(), tariff.getDailyPrice(),
                tariff.getWeeklyPrice(), tariff.getMileagePrice(), tariff.getFreeMileagePrice(), tariff.getFromDate(), tariff.getToDate());

        return new Response(result);
    }

    public Response updateTariff(CarTariff tariff) throws DuplicateEntryException, InvalidTariffDateException {

        boolean result = dao.updateCarTariff(tariff.getTariffID(), tariff.getCategoryID(), tariff.getDailyPrice(),
                tariff.getWeeklyPrice(), tariff.getMileagePrice(), tariff.getFreeMileagePrice(), tariff.getFromDate(), tariff.getToDate());

        return new Response(result);
    }

    public Response deleteTariff(Tariff tariff) throws MySQLIntegrityConstraintViolationException {

        boolean result = dao.deleteCarTariff(tariff.getTariffID());

        return new Response(result);
    }

    public Response getAllTariffs() {

        List<CarTariff> tariffs = dao.findCarTariffs();

        return new Response(tariffs);
    }

    public List<CarTariff> getAllTariffsByCategory(CarTariff tariff) {
        return dao.findCarTariffsByCategory(tariff.getCategoryID());
    }
}
