package presentation.helper.collection.cars;

import business.AccessoryManager;
import business.CarManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Accessory;
import entity.Car;
import entity.TransferObject;
import presentation.helper.Helper;


public class ChangeCarStateHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // Create Default Transfer Object
        TransferObject responce = new TransferObject();
        // cast TO to needed object
        Car car = (Car) to;

        try {
            CarManager manager = new CarManager();
            responce = manager.changeCarState(car);
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            e.printStackTrace(); //TODO exception Handler
        }

        return responce;
    }
}
