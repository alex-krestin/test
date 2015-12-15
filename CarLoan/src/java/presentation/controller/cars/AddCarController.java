package presentation.controller.cars;


import entity.Car;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.*;

public class AddCarController extends CarPopupController implements Initializable{


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fx_agency.loadValues(AGENCY);
        fx_category.loadValues(CAR_CATEGORY);

        addFieldsRestriction();
    }


    @SuppressWarnings("LawOfDemeter")
    public void handleSaveCarAction() {

        if (validateInput()) {

            Car car = new Car.Builder()
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

            executeInsert(CAR, car);
        }
    }


}
