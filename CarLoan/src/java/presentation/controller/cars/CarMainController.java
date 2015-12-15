package presentation.controller.cars;


import entity.Car;
import entity.Category;
import entity.Response;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import presentation.AlertHandler;
import presentation.FrontController;
import presentation.controller.DefaultController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.CAR;
import static presentation.helper.EntityCode.CAR_CATEGORY;
import static presentation.helper.OperationCode.GET_ALL;

public class CarMainController extends DefaultController implements Initializable{
    public TableView<Car> table;
    public TableColumn<Car, String> brandColumn;
    public TableColumn<Car, String> modelColumn;
    public TableColumn<Car, String> categoryColumn;
    public TableColumn<Car, String> yearColumn;
    public TableColumn<Car, String> mileageColumn;
    public TableColumn<Car, String> agencyCodeColumn;
    public TableColumn<Car, String> plateColumn;
    public TextField brand_filter;
    public TextField model_filter;
    public TextField plate_filter;
    public TextField agency_code_filter;
    public ObjectComboBox<Category> fx_category;

    static Car currentCar;
    private final ObservableList<Car> carList = FXCollections.observableArrayList();
    private SortedList<Car> sortedData;

    public void openAddCar() {
        if (fx_category.getItems().size() > 1) {
            FrontController.handleRequest("AddCar", "Nuovo automobile");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Prima di procedere è neccessario " +
                    "aggiungere almeno una categoria!");
        }
    }

    public void openEditCar() {
        if (currentCar != null) {
            FrontController.handleRequest("EditCar", "Modifica automobile");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessun automobile selezionato!");
        }
    }

    public void deleteCar() {
        deleteItem(currentCar, CAR);
    }

    public void gotoCategories() {
        FrontController.handleRequest("CarCategories");
    }

    public void openCarService() {
        if (currentCar != null) {
            FrontController.handleRequest("CarService", "Manutenzione automobile");
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessun automobile selezionato!");
        }
    }

    public void resetFilters() {
        resetTextFields(brand_filter, model_filter, plate_filter, agency_code_filter);
        resetComboBoxes(fx_category);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // reset currentAgency every time AgenciesView is loaded
        currentCar = null;

        fx_category.loadValues(CAR_CATEGORY);

        Category showAll = new Category.Builder("Tutti").build();
        fx_category.getItems().add(0, showAll);
        fx_category.getSelectionModel().selectFirst();

        // load all agencies from the database
        getCarsList();

        // set table columns
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        categoryColumn.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getCategoryName()));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        mileageColumn.setCellValueFactory(new PropertyValueFactory<>("mileage"));
        agencyCodeColumn.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getCurrentAgencyCode()));
        plateColumn.setCellValueFactory(new PropertyValueFactory<>("plate"));

        // prepare agency's list that can be filtered
        setSortedData();

        // insert users to the table
        table.setItems(sortedData);

        // add listener to the table rows
        table.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());
    }

    private void getCarsList() {
        if (spinnerBox.isVisible()) {
            return;
        }

        // start displaying the loading indicator at the Application Thread
        spinnerBox.setVisible(true);

        // loads the items at another thread, asynchronously
        Task dataLoader = new Task<Boolean>() {
            {
                setOnSucceeded(workerStateEvent -> {
                    spinnerBox.setVisible(false); // stop displaying the loading indicator
                });
                //setOnFailed(workerStateEvent -> getException().printStackTrace());
            }

            @Override
            protected Boolean call() throws Exception {
                // request all users from the database
                Response response = FrontController.handleRequest(GET_ALL, CAR, null);
                // extract list of objects <Agency> from TransferObject
                List<?> cars = response.getList();

                // add all agencies to ObservableList
                for(Object object : cars) {
                    Car car = (Car) object;
                    carList.add(car);
                }

                return true;
            }
        };

        Thread loadingThread = new Thread(dataLoader, "data-loader");
        loadingThread.setDaemon(true);
        loadingThread.start();
    }

    private void setSortedData() {
        // create custom filtered list

        // wrap the ObservableList in a FilteredList (initially display all users)
        FilteredList<Car> filteredByBrand = new FilteredList<>(carList, car -> true);

        // set the filter Predicate whenever the filter changes
        brand_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByBrand.setPredicate(car -> {
                // Compare city of every agency with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return car.getBrand().toLowerCase().contains(lowerCaseFilter);
            });
        });


        // update custom filter
        FilteredList<Car> customFilter = filteredByBrand;
        FilteredList<Car> filteredByModel = new FilteredList<>(customFilter, showAll -> true);

        model_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByModel.setPredicate(car -> {
                // Compare agency code of every agency with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return car.getModel().toLowerCase().contains(lowerCaseFilter);
            });
        });


        // update custom filter
        customFilter = filteredByModel;
        FilteredList<Car> filteredByPlate = new FilteredList<>(customFilter, showAll -> true);

        plate_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByPlate.setPredicate(car -> {
                // Compare agency code of every agency with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return car.getPlate().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // update custom filter
        customFilter = filteredByPlate;
        FilteredList<Car> filteredByAgencyCode = new FilteredList<>(customFilter, showAll -> true);

        agency_code_filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredByAgencyCode.setPredicate(car -> {
                // Compare agency code of every agency with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                return car.getCurrentAgencyCode().toLowerCase().contains(lowerCaseFilter);
            });
        });

        customFilter = filteredByAgencyCode;
        FilteredList<Car> filteredByCategory = new FilteredList<>(customFilter, showAll -> true);

        addCategoryFilter(fx_category, filteredByCategory);

        customFilter = filteredByCategory;

        // wrap the FilteredList in a SortedList.
        sortedData = new SortedList<>(customFilter);

        // bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(table.comparatorProperty());
    }

    private class RowSelectChangeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            int rowIndex = (int) newValue;

            if ((rowIndex == sortedData.size() || rowIndex == -1)) {
                // deselect rows to prevent ArrayIndexOutOfBoundsException
                table.getSelectionModel().clearSelection();
                // reset currentAgency
                currentCar = null;
                return; // invalid data
            }

            // update current agency to selected
            currentCar = sortedData.get(rowIndex);
        }
    }
}
