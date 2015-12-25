package it.uniba.carloan.presentation.controller.contracts;


import it.uniba.carloan.business.utility.SessionData;
import it.uniba.carloan.entity.*;
import it.uniba.carloan.javafx.scene.control.ObjectComboBox;
import it.uniba.carloan.javafx.scene.control.ObjectListView;
import it.uniba.carloan.presentation.FrontController;
import it.uniba.carloan.presentation.controller.DataLoader;
import it.uniba.carloan.presentation.helper.EntityCode;
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
import java.util.Locale;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.AVAILABLE_ACCESSORY;
import static it.uniba.carloan.presentation.helper.OperationCode.GET_ALL;

public class AddContractAccessoryTabController extends AddContractMainController implements Initializable{
    public ObjectComboBox<Category> fx_accessory_category;
    public ObjectListView<ContractObject<Accessory>> fx_list;
    public TableView<ContractObject<Accessory>> fx_table;
    public TableColumn<ContractObject<Accessory>, String> categoryColumn;
    public TableColumn<ContractObject<Accessory>, String> titleColumn;
    public TableColumn<ContractObject<Accessory>, String> inventoryCodeColumn;
    public TableColumn<ContractObject<Accessory>, String> priceColumn;
    private final ObservableList<ContractObject<Accessory>> tableData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_accessory_category.loadValues(EntityCode.ACCESSORY_CATEGORY);

        categoryColumn.setCellValueFactory(object ->
                new SimpleStringProperty(object.getValue().getObject().getCategoryName()));

        titleColumn.setCellValueFactory(object -> new SimpleStringProperty(object.getValue().getObject().getTitle()));

        inventoryCodeColumn.setCellValueFactory(object ->
                new SimpleStringProperty(object.getValue().getObject().getInventoryCode()));

        priceColumn.setCellValueFactory(object -> {
            AccessoryTariff tariff = (AccessoryTariff) object.getValue().getTariff();
            BigDecimal price = tariff.getDailyPrice();
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALIAN);
            return new SimpleStringProperty(currencyFormatter.format(price));
        });

        fx_table.setItems(tableData);
    }

    public void handleSearchAction() {
        loadAvailableAccessories();
    }

    public void addAccessoryToCart() {
        ContractObject<Accessory> currentAccessory = fx_list.getSelectionModel().getSelectedItem();

        if (currentAccessory != null) {
            tableData.add(currentAccessory);
            fx_list.getItems().remove(currentAccessory);
            fx_list.updateList();
            mainContract.getAccessories().add(currentAccessory);
        }
    }

    public void removeAccessoryFromCart() {
        ContractObject<Accessory> currentAccessory = fx_table.getSelectionModel().getSelectedItem();

        if (currentAccessory != null) {
            tableData.remove(currentAccessory);
            fx_list.getItems().add(currentAccessory);
            mainContract.getAccessories().remove(currentAccessory);
        }
    }

    @SuppressWarnings("LawOfDemeter")
    public void loadAvailableAccessories() {
        if (mainContract.getDepartureDateTime() != null
                && mainContract.getArrivalDateTime() != null && fx_accessory_category.getValue() != null) {

            Agency agency = new Agency.Builder().id(SessionData.agencyID).build();

            Accessory accessory = new Accessory.Builder().category(fx_accessory_category.getValue())
                    .currentAgency(agency).build();

            SearchRequest searchRequest = new SearchRequest.Builder<Accessory>()
                    .object(accessory)
                    .departureTime(mainContract.getDepartureTime())
                    .departureDate(mainContract.getDepartureDate())
                    .arrivalTime(mainContract.getArrivalTime())
                    .arrivalDate(mainContract.getArrivalDate()).build();

            DataLoader<ContractObject<Accessory>> dataLoader = new DataLoader<ContractObject<Accessory>>() {
                @Override
                protected Response dataLoadRequest() {
                    return FrontController.handleRequest(GET_ALL, AVAILABLE_ACCESSORY, searchRequest);
                }

                @Override
                protected HBox spinnerBox() {
                    return null;
                }
            };

            fx_list.setValues(dataLoader.getLoadedData());
        }
    }
}
