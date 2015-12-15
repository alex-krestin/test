package presentation.helper.collection.cars;

import business.appservice.CarManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Car;
import entity.Response;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import static presentation.helper.EntityCode.CAR;
import static presentation.helper.OperationCode.CHANGE_STATUS;


public class DeleteCarHelper implements Helper {
    private static final Logger log = Logger.getLogger(DeleteCarHelper.class.getName());

    @SuppressWarnings("LawOfDemeter")
    @Override
    public Response execute(TransferObject to) {
        // Create Default Transfer Object
        Response response = new Response();
        // cast TO to needed object
        Car car = (Car) to;

        try {
            CarManager manager = new CarManager();
            response = manager.deleteCar(car);
        } catch (MySQLIntegrityConstraintViolationException e) {
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
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        if (response.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Automobile è succesivamente cancellato."); //TODO Change text
        }

        return response;
    }
}
