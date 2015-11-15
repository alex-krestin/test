package presentation.helper.collection.cars;

import business.AccessoryManager;
import business.CarManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.TransferObject;
import presentation.helper.Helper;


public class GetAllCarCategoriesHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // to is null
        TransferObject responce = new TransferObject();

        try {
            CarManager manager = new CarManager();
            responce = manager.getAllCategories();
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            e.printStackTrace(); //TODO exception Handler
        }

        return responce;
    }
}
