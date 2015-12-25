package it.uniba.carloan.presentation.helper.collection.cars;

import it.uniba.carloan.business.appservice.generics.CarManager;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Car;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.uniba.carloan.business.appservice.ServiceFactory.getApplicationService;


public class DeleteCarHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeleteCarHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        Car car = (Car) to;

        try {
            CarManager manager = getApplicationService().getCarManager();
            response = manager.deleteCar(car);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        } catch (IntegrityConstraintViolationException e) {
            e.printStackTrace();
        }


        /*
        catch (IntegrityConstraintViolationException e) {
            //TODO Change text
            Boolean result = AlertHandler.showAlertConfirmationDialog("Errore di cancellazione",
                    "Impossibile cancellare automobile. Vuoi procedere con l'archivazione", "Ci sono i dati collegati " +
                            "all'automobile. Tuttavia è possibile disabilitare l'automobile procedendo con l'archivazione. " +
                            "In questo caso l'automobile verra disabilitato ma i dati non verranno cancellati.",
                    "Archivia");
            if(result) {
                response = FrontController.handleRequest(CHANGE_STATUS, CAR, car);

                if (response.getOperationResult()) {
                    AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Automobile è succesivamente archiviato.");
                }

                return response;
            }


        if (response.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Automobile è succesivamente cancellato."); //TODO Change text
        }*/

        return response;
    }
}
