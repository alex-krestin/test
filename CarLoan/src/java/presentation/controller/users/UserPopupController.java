package presentation.controller.users;


import entity.Agency;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ObjectComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import presentation.helper.EntityCode;

public class UserPopupController extends UserMainController {
    public TextField fx_name;
    public TextField fx_surname;
    public TextField fx_username;
    public TextField fx_password;
    public TextField fx_repeat_password;
    public ComboBox<String> fx_account_type;
    public ObjectComboBox<Agency> fx_agencies;
    public CheckBox fx_access_granted;
    public GridPane gridPane;


    void initializeComboBoxes() {
        fx_account_type.getItems().addAll("Operatore", "Amministratore");
        fx_account_type.getSelectionModel().selectFirst();
        fx_agencies.loadValues(EntityCode.AGENCY);
    }
}
