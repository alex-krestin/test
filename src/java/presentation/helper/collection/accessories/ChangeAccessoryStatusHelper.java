package presentation.helper.collection.accessories;

import business.AccessoryManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Accessory;
import entity.TransferObject;
import presentation.helper.Helper;


public class ChangeAccessoryStatusHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // Create Default Transfer Object
        TransferObject responce = new TransferObject();
        // cast TO to needed object
        Accessory accessory = (Accessory) to;

        try {
            AccessoryManager manager = new AccessoryManager();
            responce = manager.changeAccessoryState(accessory);
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            e.printStackTrace(); //TODO exception Handler
        }

        return responce;
    }
}
