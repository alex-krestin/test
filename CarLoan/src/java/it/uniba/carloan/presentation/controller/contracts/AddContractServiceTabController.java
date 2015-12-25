package it.uniba.carloan.presentation.controller.contracts;


import it.uniba.carloan.entity.*;
import it.uniba.carloan.javafx.scene.control.ObjectListView;
import it.uniba.carloan.presentation.FrontController;
import it.uniba.carloan.presentation.controller.DataLoader;
import it.uniba.carloan.presentation.helper.OperationCode;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.AVAILABLE_SERVICE;

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

            DataLoader<ContractObject<Service>> dataLoader = new DataLoader<ContractObject<Service>>() {
                @Override
                protected Response dataLoadRequest() {
                    return FrontController.handleRequest(OperationCode.GET_ALL, AVAILABLE_SERVICE, contract);
                }

                @Override
                protected HBox spinnerBox() {
                    return null;
                }
            };

            fx_service_list.setValues(dataLoader.getLoadedData());
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
