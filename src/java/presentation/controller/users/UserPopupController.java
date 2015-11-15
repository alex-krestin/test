package presentation.controller.users;


import entity.Agency;
import entity.TransferObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import presentation.FrontController;

import java.util.ArrayList;

import static presentation.helper.EntityCode.AGENCY;
import static presentation.helper.OperationCode.GET_ALL;

public class UserPopupController extends UserMainController {
    public TextField fx_name;
    public TextField fx_surname;
    public TextField fx_username;
    public TextField fx_password;
    public TextField fx_repeat_password;
    public ComboBox<String> fx_account_type;
    public ComboBox<Agency> fx_agencies;
    public CheckBox fx_access_granted;
    public ImageView fx_name_validation;
    public ImageView fx_surname_validation;
    public ImageView fx_username_validation;
    public ImageView fx_password_validation;
    public ImageView fx_repeat_password_validation;
    public Label fx_name_label;
    public Label fx_surname_label;
    public Label fx_username_label;
    public Label fx_password_label;
    public Label fx_repeat_password_label;
    public Label fx_error_message;

    protected void initializeComboBoxes() {
        fx_account_type.getItems().addAll("Operatore", "Amministratore");
        fx_account_type.getSelectionModel().selectFirst();
        setAgenciesList();
    }

    private void setAgenciesList() {
        TransferObject response = FrontController.handleRequest(GET_ALL, AGENCY, null);
        ArrayList<?> agencies = response.getArrayList();

        ObservableList<Agency> list = FXCollections.observableArrayList();

        for(Object object : agencies) {
            Agency agency = (Agency) object;
            list.add(agency);
        }

        fx_agencies.setItems(list);
        fx_agencies.getSelectionModel().selectFirst();
        fx_agencies.setCellFactory(new Callback<ListView<Agency>, ListCell<Agency>>() {
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


        fx_agencies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            currentAgency = newValue;
        });

    }
}
