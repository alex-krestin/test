package presentation.helper.collection.penalties;

import business.appservice.PenaltyManager;
import dao.exception.DuplicateEntryException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Category;
import entity.Response;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class AddPenaltyCategoryHelper implements Helper {
    private static final Logger log = Logger.getLogger(AddPenaltyCategoryHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        Category category = (Category) to;

        try {
            PenaltyManager manager = new PenaltyManager();
            response = manager.addCategory(category);
        } catch (DuplicateEntryException e) {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione!", e.getCustomMessage());
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
