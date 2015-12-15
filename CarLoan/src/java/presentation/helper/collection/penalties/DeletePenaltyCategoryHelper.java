package presentation.helper.collection.penalties;

import business.appservice.PenaltyManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Category;
import entity.Response;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class DeletePenaltyCategoryHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeletePenaltyCategoryHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        Category category = (Category) to;

        try {
            PenaltyManager manager = new PenaltyManager();
            response = manager.deleteCategory(category);
        } catch (MySQLIntegrityConstraintViolationException e) {
            //TODO Change text
            AlertHandler.showAlert(Alert.AlertType.ERROR, "Errore di cancellazione",
                    "Impossibile eliminare categoria in quanto non è vuota. Per eliminare sposta o elimina prima " +
                            "tutti gli elementi in questa categoria e riprova.");
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        if (response.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Categoria è succesivamente eliminata."); //TODO Change text
        }

        return response;
    }
}
