package presentation.helper.collection.services;

import business.appservice.ServiceManager;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.Service;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateServiceHelper implements Helper {
    private static final Logger log = Logger.getLogger(UpdateServiceHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        Service service = (Service) to;

        try {
            ServiceManager manager = new ServiceManager();
            response = manager.updateService(service);
        } catch (DuplicateEntryException e) {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", e.getCustomMessage());
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
