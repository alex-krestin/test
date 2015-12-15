package presentation.controller.contracts;

import entity.Client;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.controller.DefaultController;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.CLIENT;


public class ContractMainController extends DefaultController implements Initializable {

    public TableView<Client> table;
    public TableColumn<Client, String> nameColumn;
    public TableColumn<Client, String> surnameColumn;
    public TableColumn<Client, String> sexColumn;
    public TableColumn<Client, String> fiscalCodeColumn;
    public TableColumn<Client, String> birthdayColumn;
    public TableColumn<Client, String> phoneColumn;
    public TableColumn<Client, String> commentColumn;
    public TextField name_filter;
    public TextField surname_filter;
    public TextField fiscal_code_filter;
    public TextField phone_filter;


    static Client currentClient;
    private final ObservableList<Client> clientList = FXCollections.observableArrayList();

    private SortedList<Client> sortedData;

    public void openAddClient() {
        FrontController.handleRequest("AddContract", "Nuovo contratto");
    }

    public void openEditClient() {
        if (currentClient != null) {
            FrontController.handleRequest("EditClient", "Modifica cliente");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessun cliente selezionato!");
        }
    }

    public void deleteClient() {
        deleteItem(currentClient, CLIENT);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    private void setSortedData() {

    }

    public void handleSaveContractAction(ActionEvent actionEvent) {
    }

    public void handleSearchAction(ActionEvent actionEvent) {
    }

    private class RowSelectChangeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            int rowIndex = (int) newValue;

            if ((rowIndex == sortedData.size() || rowIndex == -1)) {
                // deselect rows to prevent ArrayIndexOutOfBoundsException
                table.getSelectionModel().clearSelection();
                // reset currentUser
                currentClient = null;
                return; // invalid data
            }

            // update current user
            currentClient = sortedData.get(rowIndex);
        }
    }

    private void getAllClients() {

    }

    public void resetFilters() {
        resetTextFields(name_filter, surname_filter, fiscal_code_filter, phone_filter);
    }


}
