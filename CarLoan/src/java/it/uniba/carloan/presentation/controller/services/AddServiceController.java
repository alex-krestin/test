package it.uniba.carloan.presentation.controller.services;

import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.Service;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.FrontController;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.SERVICE;
import static it.uniba.carloan.presentation.helper.OperationCode.ADD;


public class AddServiceController extends ServicePopupController implements Initializable{
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @SuppressWarnings("LawOfDemeter")
    public void handleSaveServiceAction() {
        if (validateInput()) {

            Service service = new Service.Builder()
                    .title(fx_title.getText())
                    .description(fx_description.getText()).build();

            Response response = FrontController.handleRequest(ADD, SERVICE, service);

            if(response.getOperationResult()) {
                closeWindow();
                AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Servizio \u00E8 aggiunto correttamente.");
                // Update Services view
                FrontController.handleRequest("Services");
            }
        }
    }
}
