package it.uniba.carloan.presentation.helper.collection.accessories;

import it.uniba.carloan.business.appservice.generics.AccessoryManager;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Category;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;
import javafx.scene.control.Alert;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;
import static it.uniba.carloan.presentation.AlertHandler.showAlert;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class DeleteAccessoryCategoryHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeleteAccessoryCategoryHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        Category category = (Category) to;
        Response response = new Response();

        try {
            AccessoryManager manager = getApplicationService().getAccessoryManager();
            response = manager.deleteCategory(category);
            showAlert(INFORMATION, "Successo", "Categoria è succesivamente eliminata."); //TODO Change text
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        } catch (IntegrityConstraintViolationException e) {
            //TODO Change text
            showAlert(Alert.AlertType.ERROR, "Errore di cancellazione",
                    "Impossibile eliminare categoria in quanto non è vuota. Per eliminare sposta o elimina prima " +
                            "tutti gli accessori in questa categoria e riprova.");
        }

        return response;
    }
}
