package it.uniba.carloan.presentation.helper.collection.penalties;

import it.uniba.carloan.business.appservice.generics.PenaltyManager;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Category;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;


public class DeletePenaltyCategoryHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeletePenaltyCategoryHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        Category category = (Category) to;

        try {
            PenaltyManager manager = getApplicationService().getPenaltyManager();
            response = manager.deleteCategory(category);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        } catch (IntegrityConstraintViolationException e) {
            e.printStackTrace();
        }


        /*
        catch (IntegrityConstraintViolationException e) {
            //TODO Change text
            AlertHandler.showAlert(Alert.AlertType.ERROR, "Errore di cancellazione",
                    "Impossibile eliminare categoria in quanto non è vuota. Per eliminare sposta o elimina prima " +
                            "tutti gli elementi in questa categoria e riprova.");
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        if (response.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Categoria è succesivamente eliminata."); //TODO Change text
        }*/

        return response;
    }
}
