package presentation.helper.collection.cars;

import business.AccessoryManager;
import business.CarManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.exception.UnknownDatabaseTypeException;
import entity.Accessory;
import entity.Car;
import entity.TransferObject;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.helper.Helper;

import static presentation.helper.EntityCode.ACCESSORY;
import static presentation.helper.EntityCode.CAR;
import static presentation.helper.OperationCode.CHANGE_STATUS;


public class DeleteCarHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // Create Default Transfer Object
        TransferObject responce = new TransferObject();
        // cast TO to needed object
        Car car = (Car) to;

        try {
            CarManager manager = new CarManager();
            responce = manager.deleteCar(car);
        } catch (MySQLIntegrityConstraintViolationException e) {
            //TODO Change text
            Boolean result = AlertHandler.showAlertConfirmationDialog("Errore di cancellazione",
                    "Impossibile cancellare automobile. Vuoi procedere con l'archivazione", "Ci sono i dati collegati " +
                            "all'automobile. Tuttavia è possibile disabilitare l'automobile procedendo con l'archivazione. " +
                            "In questo caso l'automobile verra disabilitato ma i dati non verranno cancellati.",
                    "Archivia");
            if(result) {
                responce = FrontController.handleRequest(CHANGE_STATUS, CAR, car);

                if (responce.getOperationResult()) {
                    AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Automobile è succesivamente archiviato.");
                }

                return responce;
            }
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            e.printStackTrace(); //TODO exception Handler
        }

        if (responce.getOperationResult()) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Automobile è succesivamente cancellato."); //TODO Change text
        }

        return responce;
    }
}
