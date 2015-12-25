package it.uniba.carloan.presentation.controller.services;

import it.uniba.carloan.entity.Service;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.SERVICE;


public class EditServiceController extends ServicePopupController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_title.setText(currentService.getTitle());
        fx_description.setText(currentService.getDescription());
    }

    @SuppressWarnings("LawOfDemeter")
    public void handleEditServiceAction() {
        if (validateInput()) {

            Service service = new Service.Builder()
                    .id(currentService.getId())
                    .title(fx_title.getText())
                    .description(fx_description.getText()).build();

            executeUpdate(currentService, service, SERVICE);
        }
    }
}