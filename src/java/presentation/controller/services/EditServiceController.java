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
import static presentation.helper.OperationCode.UPDATE;


public class EditServiceController extends ServicePopupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_title.setText(currentService.getTitle());
        fx_description.setText(currentService.getDescription());
    }

    public void handleEditServiceAction() {
        if (validateInput()) {
            String title = fx_title.getText();
            String description = fx_description.getText();


            Service service = new Service(currentService.getId(), title, description);

            // Update service only if there are any changes
            if(!service.equals(currentService)) {
                TransferObject response = FrontController.handleRequest(UPDATE, SERVICE, service);

                if(response.getOperationResult()) {
                    closeWindow();
                    AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Servizio \u00E8 succesivamente aggiornato.");
                    // Update Agencies view
                    FrontController.handleRequest("Services");
                }
            }
            else {
                closeWindow();
            }
        }
    }
}