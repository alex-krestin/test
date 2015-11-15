package presentation.helper.collection.services;

import business.ServiceManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Service;
import entity.TransferObject;
import presentation.helper.Helper;


public class ChangeServiceStatusHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // Create Default Transfer Object
        TransferObject responce = new TransferObject();
        // cast TO to needed object
        Service service = (Service) to;

        try {
            ServiceManager manager = new ServiceManager();
            responce = manager.changeServiceStatus(service);
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return responce;
    }
}
