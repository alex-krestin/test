package business;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.DAOFactory;
import dao.entity.SystemDAO;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import dao.entity.CarDAO;
import entity.Agency;
import entity.Car;
import entity.Category;
import entity.TransferObject;
import utility.SessionData;

import java.util.ArrayList;


public class CarManager {
    private CarDAO dao;

    public CarManager() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        this.dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getCarDAO();
    }

    public TransferObject getAllCars() {
        ArrayList<Car> cars = dao.getAllCars();
        return new TransferObject(cars);
    }

    public TransferObject getAllCars(Agency agency) {
        ArrayList<Car> cars = dao.getAllCars(agency.getAgencyID());
        return new TransferObject(cars);
    }

    public TransferObject addCar(Car car) throws DuplicateEntryException {
        boolean result;

        result = dao.addCar(car.getBrand(), car.getModel(), car.getYear(), car.getCategory().getId(), car.getCurrentAgency().getAgencyID(),
                car.getPlate(), car.getDoors(), car.getPassengers(), car.getDescription(), car.getMileage());

        return new TransferObject(result);
    }

    public TransferObject updateCar(Car car) throws DuplicateEntryException {
        boolean result;

        result = dao.updateCar(car.getId(), car.getBrand(), car.getModel(), car.getYear(), car.getCategory().getId(),
                car.getPlate(), car.getDoors(), car.getPassengers(), car.getDescription());

        return new TransferObject(result);
    }

    public TransferObject deleteCar(Car car) throws MySQLIntegrityConstraintViolationException {
        boolean result;
        result = dao.deleteCar(car.getId());
        return new TransferObject(result);
    }

    public TransferObject changeCarState(Car car) {
        boolean result;

        if(car.isAvailable()) {
            car.setAvailable(false);
        }
        else {
            car.setAvailable(true);
        }
        result = dao.changeCarStatus(car.getId(), car.isAvailable());
        return new TransferObject(result);
    }

    public TransferObject getAllCategories() {
        ArrayList<Category> categories = dao.getAllCategories();
        return new TransferObject(categories);
    }

    public TransferObject addCategory(Category category) throws DuplicateEntryException {
        boolean result;

        System.out.println(category.getName());
        System.out.println(category.getDescription());

        result = dao.addCategory(category.getName(), category.getDescription());

        return new TransferObject(result);
    }

    public TransferObject updateCategory(Category category) throws DuplicateEntryException {
        boolean result;

        result = dao.updateCategory(category.getId(), category.getName(), category.getDescription());

        return new TransferObject(result);
    }

    public TransferObject deleteCategory(Category category) throws MySQLIntegrityConstraintViolationException {
        boolean result;

        result = dao.deleteCategory(category.getId());

        return new TransferObject(result);
    }
}
