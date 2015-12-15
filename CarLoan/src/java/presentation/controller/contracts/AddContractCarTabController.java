package presentation.controller.contracts;


import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import presentation.FrontController;
import presentation.controller.InputValidationController;
import presentation.helper.EntityCode;
import presentation.validation.GridValidator;
import utility.SessionData;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.AVAILABLE_CAR;
import static presentation.helper.OperationCode.GET_ALL;
import static presentation.validation.ValidationConstraint.*;

public class AddContractCarTabController extends AddContractMainController implements Initializable {

    public ComboBox<String> fx_tariff_type;
    public ObjectComboBox<Category> fx_car_category;
    public ObjectComboBox<Agency> fx_agency;
    public RadioButton fx_estimate;
    public ToggleGroup contract_type;
    public RadioButton fx_booking;
    public RadioButton fx_emission;
    public CustomTextField fx_mileage_value;
    public CheckBox fx_free_mileage;
    public TimePicker fx_departure_time;
    public DatePicker fx_departure_date;
    public TimePicker fx_arrival_time;
    public CustomDatePicker fx_arrival_date;
    public TextArea fx_comment;
    public ObjectComboBox<ContractObject<Car>> fx_available_car;
    public GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_car_category.loadValues(EntityCode.CAR_CATEGORY);
        fx_agency.loadValues(EntityCode.AGENCY);
        addSummaryUpdateListeners();

        fx_departure_date.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fx_arrival_date.disableBefore(fx_departure_date.getValue());

                if (fx_arrival_date.getValue() != null && newValue.isAfter(fx_arrival_date.getValue())) {
                    fx_arrival_date.setValue(null);
                }
            }

            // Enable toDate after fromDate selection
            if(fx_arrival_date.isDisabled()) {
                fx_arrival_date.disableProperty().set(false);
            }
        });

        fx_arrival_date.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.isBefore(fx_departure_date.getValue())) {
                fx_arrival_date.setValue(null);
            }
        });


        ObservableList<String> tariffs = FXCollections.observableArrayList("Giornaliera", "Settimanale");
        fx_tariff_type.setItems(tariffs);
        fx_tariff_type.getSelectionModel().selectFirst();

        fx_mileage_value.limitToNumbers();

        fx_free_mileage.selectedProperty().addListener((observable, oldValue, newValue) -> {
            fx_mileage_value.disableProperty().set(false);

            if(newValue) {
                fx_mileage_value.disableProperty().set(true);
            }
        });
    }


    @SuppressWarnings("LawOfDemeter")
    public boolean validateInput() {
        GridValidator gv = new GridValidator(gridPane);
        gv.addConstraint(NOT_NULL).validate(fx_departure_time, fx_departure_date);
        gv.addConstraint(NOT_NULL).validate(fx_arrival_time, fx_arrival_date);
        gv.addConstraint(NOT_NULL).validate(fx_agency);
        if(!gv.addConstraint(CHECKED).showError(false).validate(fx_free_mileage)) {
            gv.addConstraint(NOT_EMPTY).validate(fx_mileage_value);
        }
        gv.addConstraint(NOT_NULL).validate(fx_car_category);
        gv.addConstraint(NOT_NULL).validate(fx_available_car);



       //TODO

        // set dates
        InputValidationController validator = new InputValidationController();

        /*
        if (fx_departure_date.getValue() != null) {
            departure = compactDateTimePicker(fx_departure_date, fx_departure_time);
        }
        if (fx_arrival_date.getValue() != null) {
            arrival = compactDateTimePicker(fx_arrival_date, fx_arrival_time);
        }

        // Perform validations
        validator.validateDate(null, null, fx_departure_date, fx_departure_date_validation, fx_departure_date_label);
        validator.validateDate(null, null, fx_arrival_date, fx_arrival_date_validation, fx_arrival_date_label);

        if(fx_departure_date.getValue() != null && fx_arrival_date.getValue() != null) {
            validator.validateTimeInterval(fx_departure_date, fx_departure_time, fx_arrival_date, fx_arrival_time,
                    fx_arrival_date_validation, fx_arrival_date_label);
        }


        if(!fx_free_mileage.isSelected()) {
            validator.isEmpty(fx_mileage_value, fx_mileage_validation, fx_mileage_label);
        }
        else validator.setValid(fx_mileage_validation);

        validator.showFirstError(fx_error_message);
*/
        return gv.isValid();
    }

    @SuppressWarnings("LawOfDemeter")
    public void handleSearchCars() {
        if(validateSearchInput()) {

            Agency agency = new Agency.Builder().id(SessionData.agencyID).build();

            Car car = new Car.Builder()
                    .category(fx_car_category.getValue())
                    .currentAgency(agency).build();

            SearchRequest searchRequest = new SearchRequest.Builder<>()
                    .object(car)
                    .departureAgency(agency)
                    .departureDate(fx_departure_date.getValue())
                    .departureTime(fx_departure_time.getValue())
                    .arrivalDate(fx_arrival_date.getValue())
                    .arrivalTime(fx_arrival_time.getValue()).build();

            Response response = FrontController.handleRequest(GET_ALL, AVAILABLE_CAR, searchRequest);

            List<?> cars = response.getList();
            ObservableList<ContractObject<Car>> availableCarsList = FXCollections.observableArrayList();

            // add all agencies to ObservableList
            for(Object object : cars) {
                @SuppressWarnings("unchecked")
                ContractObject<Car> availableCar = (ContractObject<Car>) object;
                availableCarsList.add(availableCar);
            }

            fx_available_car.setValues(availableCarsList);
        }
    }

    @SuppressWarnings("LawOfDemeter")
    private boolean validateSearchInput() {
        GridValidator gv = new GridValidator(gridPane);
        gv.addConstraint(NOT_NULL).validate(fx_departure_time, fx_departure_date);
        gv.addConstraint(NOT_NULL).validate(fx_arrival_time, fx_arrival_date);
        gv.addConstraint(NOT_NULL).validate(fx_car_category);
        return gv.isValid();
    }


    /**************************
     * LISTENERS TO UPDATE CONTRACT SUMMARY
     *************************/

    private void addSummaryUpdateListeners() {

        fx_departure_time.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            mainContract.setDepartureTime(newValue);
        });

        fx_departure_date.valueProperty().addListener((observable, oldValue, newValue) -> {
            mainContract.setDepartureDate(newValue);
        });

        fx_arrival_time.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            mainContract.setArrivalTime(newValue);
        });

        fx_arrival_date.valueProperty().addListener((observable, oldValue, newValue) -> {
            mainContract.setArrivalDate(newValue);
        });

        fx_mileage_value.textProperty().addListener((observable, oldValue, newValue) -> {
            mainContract.setMileage(fx_mileage_value.getIntegerValue());
        });

        fx_free_mileage.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                mainContract.setMileage(null);
                mainContract.setFreeMileageOption(true);
            }
            else {
                mainContract.setMileage(fx_mileage_value.getIntegerValue());
                mainContract.setFreeMileageOption(false);
            }
        });

        fx_tariff_type.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            mainContract.setTariffType(newValue.intValue());
        });

        fx_available_car.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            mainContract.setCar(newValue);
        });

        fx_agency.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            mainContract.setArrivalAgency(newValue);
        });

        fx_comment.textProperty().addListener((observable, oldValue, newValue) -> {
            mainContract.setNotes(fx_comment.getText());
        });
    }

}
