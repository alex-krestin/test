package presentation.controller.accessories;


import entity.Accessory;
import entity.Agency;
import entity.Category;
import entity.TransferObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.helper.exception.DataLoadingException;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.ACCESSORY;
import static presentation.helper.EntityCode.AGENCY;
import static presentation.helper.OperationCode.ADD;
import static presentation.helper.OperationCode.GET_ALL;

public class AddAccessoryController extends AccessoryPopupController implements Initializable {

    public ComboBox<Agency> fx_agency;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCategoriesList();
        setAgencyList();
    }


    private void setAgencyList() {
        TransferObject response = FrontController.handleRequest(GET_ALL, AGENCY, null);
        ArrayList<?> agencies = response.getArrayList();

        ObservableList<Agency> list = FXCollections.observableArrayList();

        for(Object object : agencies) {
            Agency agency = (Agency) object;
            list.add(agency);
        }

        fx_agency.setItems(list);
        fx_agency.getSelectionModel().selectFirst();
        fx_agency.setCellFactory(new Callback<ListView<Agency>, ListCell<Agency>>() {
            @Override
            public ListCell<Agency> call(ListView<Agency> param) {

                return new ListCell<Agency>(){
                    @Override
                    public void updateItem(Agency agency, boolean empty){
                        super.updateItem(agency, empty);
                        if(!empty) {
                            setText(agency.toString());
                            setGraphic(null);
                        }
                    }
                };
            }
        });
    }

    public void handleSaveAccessoryAction() {

        if (validateInput()) {

            String title =  fx_title.getText();
            String description = fx_description.getText();
            String inventoryCode = fx_icode.getText();
            Category category = fx_category.getValue();
            Agency agency = fx_agency.getValue();

            Accessory accessory = new Accessory(title, description, inventoryCode, category, agency);

            TransferObject response = FrontController.handleRequest(ADD, ACCESSORY, accessory);

            if(response.getOperationResult()) {
                closeWindow();
                AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Acccessorio \u00E8 aggiunto correttamente.");
                // Update Users view
                FrontController.handleRequest("Accessories");
            }

        }
    }
}
