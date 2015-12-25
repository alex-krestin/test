package it.uniba.carloan.presentation.controller.contracts;


import it.uniba.carloan.entity.Contract;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.controller.DefaultController;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.WARNING;

public class AddContractMainController extends DefaultController implements Initializable {

    public AddContractCarTabController carTabController;
    public AddContractClientTabController clientTabController;
    public AddContractServiceTabController serviceTabController;
    public AddContractSummaryTabController summaryTabController;

    static final Contract mainContract = new Contract();
    public TabPane fx_tabs;
    public Tab fx_summary_tab;
    public Tab fx_service_tab;
    public Tab fx_accessory_tab;


    public void handleSaveContractAction() {
        if (!carTabController.validateInput()) {
            AlertHandler.showAlert(WARNING, "Attenzione", "Controlla i dati dell'auto!");
        }
        else if (!clientTabController.validateInput()) {
            AlertHandler.showAlert(WARNING, "Attenzione", "Controlla i dati del cliente!");
        }
        else {
            clientTabController.addClientToContract();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_tabs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(fx_summary_tab)) {
                summaryTabController.update();
            }
            else if(newValue.equals(fx_service_tab)) {
                serviceTabController.update();
            }
        });
    }

}
