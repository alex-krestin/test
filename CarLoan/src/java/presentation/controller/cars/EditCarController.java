package presentation.controller.cars;


import entity.Car;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.CAR;
import static presentation.helper.EntityCode.CAR_CATEGORY;

public class EditCarController extends CarPopupController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_brand.setText(currentCar.getBrand());
        fx_model.setText(currentCar.getModel());
        fx_year.setText(currentCar.getYear());
        fx_plate.setText(currentCar.getPlate());
        fx_passengers.setText(String.valueOf(currentCar.getPassengers()));
        fx_doors.setText(String.valueOf(currentCar.getDoors()));
        fx_mileage.setText(String.valueOf(currentCar.getMileage()));
        fx_category.loadValues(CAR_CATEGORY);
        fx_category.getSelectionModel().select(currentCar.getCategory());
        fx_description.setText(currentCar.getDescription());
        addFieldsRestriction();
    }

    @SuppressWarnings("LawOfDemeter")
    public void handleEditCarAction() {

        if (validateInput()) {

            Car car = new Car.Builder()
                    .carID(currentCar.getId())
                    .brand(fx_brand.getText())
                    .model(fx_model.getText())
                    .year(fx_year.getIntegerValue())
                    .plate(fx_plate.getText())
                    .passengers(fx_passengers.getIntegerValue())
                    .doors(fx_doors.getIntegerValue())
                    .mileage(fx_mileage.getIntegerValue())
                    .category(fx_category.getValue())
                    .currentAgency(fx_agency.getValue())
                    .description(fx_description.getText()).build();

            executeUpdate(currentCar, car, CAR);
        }
    }
}