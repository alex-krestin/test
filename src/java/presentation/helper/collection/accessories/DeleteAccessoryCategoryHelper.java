package presentation.helper.collection.accessories;

import business.AccessoryManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Category;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.helper.Helper;

public class DeleteAccessoryCategoryHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // Create Default Transfer Object
        TransferObject responce = new TransferObject();
        // cast TO to needed object
        Category category = (Category) to;

        try {
            AccessoryManager manager = new AccessoryManager();
            responce = manager.deleteCategory(category);
        } catch (MySQLIntegrityConstraintViolationException e) {
            //TODO Change text
            AlertHandler.showAlert(Alert.AlertType.ERROR, "Errore di cancellazione",
                    "Impossibile eliminare categoria in quanto non è vuota. Per eliminare sposta o elimina prima " +
                            "tutti gli accessori in questa categoria e riprova.");
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            e.printStackTrace(); //TODO exception Handler
        }

        if (responce.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Categoria è succesivamente eliminata."); //TODO Change text
        }

        return responce;
    }
}
