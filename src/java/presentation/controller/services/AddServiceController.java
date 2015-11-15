package presentation.controller.services;

import entity.Service;
import entity.TransferObject;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import presentation.AlertHandler;
import presentation.FrontController;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.SERVICE;
import static presentation.helper.OperationCode.ADD;


public class AddServiceController extends ServicePopupController implements Initializable{
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleSaveServiceAction() {
        if (validateInput()) {
            String title = fx_title.getText();
            String description = fx_description.getText();

            Service service = new Service(title, description);

            TransferObject response = FrontController.handleRequest(ADD, SERVICE, service);

            if(response.getOperationResult()) {
                closeWindow();
                AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Servizio \u00E8 aggiunto correttamente.");
                // Update Services view
                FrontController.handleRequest("Services");
            }
        }
    }
}
