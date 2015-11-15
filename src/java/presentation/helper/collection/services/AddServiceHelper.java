package presentation.helper.collection.services;

import business.ServiceManager;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Service;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.helper.Helper;

public class AddServiceHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // Create Default Transfer Object
        TransferObject responce = new TransferObject();
        // cast TO to needed object
        Service service = (Service) to;

        try {
            ServiceManager serviceManager = new ServiceManager();
            responce = serviceManager.addService(service);
        } catch (DuplicateEntryException e) {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione!", e.getCustomMessage());
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return responce;
    }
}
