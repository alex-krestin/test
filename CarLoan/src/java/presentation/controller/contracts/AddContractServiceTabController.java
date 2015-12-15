package presentation.controller.contracts;


import entity.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ObjectListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import presentation.FrontController;
import presentation.helper.OperationCode;

import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.AVAILABLE_SERVICE;

public class AddContractServiceTabController extends AddContractMainController implements Initializable{

    public ObjectListView<ContractObject<Service>> fx_service_list;
    public TableView<ContractObject<Service>> fx_service_table;
    public TableColumn<ContractObject<Service>, String> titleColumn;
    public TableColumn<ContractObject<Service>, String>  priceColumn;
    private final ObservableList<ContractObject<Service>> tableData = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleColumn.setCellValueFactory(object -> new SimpleStringProperty(object.getValue().getObject().getTitle()));
        priceColumn.setCellValueFactory(object -> {
            ServiceTariff tariff = (ServiceTariff) object.getValue().getTariff();
            BigDecimal price = tariff.getPrice();
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALIAN);
            return new SimpleStringProperty(currencyFormatter.format(price));
        });

        fx_service_table.setItems(tableData);
    }

    public void loadAvailableServices(LocalDate date) {
        Contract contract = new Contract();

        if (date != null) {
            contract.setDepartureDate(date);
            Response response = FrontController.handleRequest(OperationCode.GET_ALL, AVAILABLE_SERVICE, contract);

            List<?> services = response.getList();
            ObservableList<ContractObject<Service>> availableServicesList = FXCollections.observableArrayList();

            // add all agencies to ObservableList
            for(Object object : services) {
                @SuppressWarnings("unchecked")
                ContractObject<Service> availableService = (ContractObject<Service>) object;
                availableServicesList.add(availableService);
            }

            fx_service_list.setValues(availableServicesList);
        }
    }

    public void addServiceToCart() {
        ContractObject<Service> currentService = fx_service_list.getSelectionModel().getSelectedItem();

        if (currentService != null) {
            tableData.add(currentService);
            fx_service_list.getItems().remove(currentService);
            fx_service_list.updateList();
            // add service to contract
            mainContract.getServices().add(currentService);
        }
    }

    public void removeServiceFromCart() {
        ContractObject<Service> currentService = fx_service_table.getSelectionModel().getSelectedItem();

        if (currentService != null) {
            tableData.remove(currentService);
            fx_service_list.getItems().add(currentService);
            // add service from contract
            mainContract.getServices().remove(currentService);
        }
    }

    public void update() {
        if(mainContract.getDepartureDate() != null && tableData.size() == 0) {
            loadAvailableServices(mainContract.getDepartureDate());
        }
    }
}
