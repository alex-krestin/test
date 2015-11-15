package presentation.helper.collection.accessories;

import business.AccessoryManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.TransferObject;
import presentation.helper.Helper;


public class GetAllAccessoryCategoriesHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // to is null
        TransferObject responce = new TransferObject();

        try {
            AccessoryManager manager = new AccessoryManager();
            responce = manager.getAllCategories();
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            e.printStackTrace(); //TODO exception Handler
        }

        return responce;
    }
}
