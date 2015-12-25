package it.uniba.carloan.presentation.controller.services;

import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.Service;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.FrontController;
import it.uniba.carloan.presentation.controller.DataLoader;
import it.uniba.carloan.presentation.controller.DefaultController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.SERVICE;
import static it.uniba.carloan.presentation.helper.OperationCode.GET_ALL;

public class ServiceMainController extends DefaultController implements Initializable{

    public TableView<Service> table;
    public TableColumn<Service, String> titleColumn;
    public TableColumn<Service, String> descriptionColumn;

    private ObservableList<Service> loadedData = FXCollections.observableArrayList();
    static Service currentService;


    public void openAddService() {
        FrontController.handleRequest("AddService", "Nuovo servizio");
    }

    public void openEditService() {
        if (currentService != null) {
            FrontController.handleRequest("EditService", "Aggiorna servizio");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessun servizio selezionato!");
        }
    }

    public void deleteService() {
        deleteItem(currentService, SERVICE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentService = null;

        // set table columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        DataLoader<Service> dataLoader = new DataLoader<Service>() {
            @Override
            protected Response dataLoadRequest() {
                return FrontController.handleRequest(GET_ALL, SERVICE, null);
            }

            @Override
            protected HBox spinnerBox() {
                return spinnerBox;
            }
        };

        loadedData = dataLoader.getLoadedData();
        // insert services to the table
        table.setItems(loadedData);

        // add listener to the table rows
        table.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());
    }


    private class RowSelectChangeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            int rowIndex = (int) newValue;

            if ((rowIndex == loadedData.size() || rowIndex == -1)) {
                // deselect rows to prevent ArrayIndexOutOfBoundsException
                table.getSelectionModel().clearSelection();
                // reset currentService
                currentService = null;
                return; // invalid data
            }

            // update current service to selected
            currentService = loadedData.get(rowIndex);
        }
    }
}

