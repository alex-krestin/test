package it.uniba.carloan.presentation.helper.collection.cars;

import it.uniba.carloan.business.appservice.ServiceFactory;
import it.uniba.carloan.business.appservice.generics.CarManager;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Category;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class AddCarCategoryHelper implements Helper {
    private static final Logger log = Logger.getLogger(AddCarCategoryHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        Category category = (Category) to;

        try {
            CarManager manager = ServiceFactory.getApplicationService().getCarManager();
            response = manager.addCategory(category);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        } catch (DuplicateEntryException e) {
            e.showAlert();
        }

        return response;
    }
}
