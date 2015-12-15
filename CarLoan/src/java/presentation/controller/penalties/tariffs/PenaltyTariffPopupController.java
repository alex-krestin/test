package presentation.controller.penalties.tariffs;


import entity.Penalty;
import entity.PenaltyTariff;
import entity.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import presentation.FrontController;
import presentation.validation.GridValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static presentation.helper.EntityCode.PENALTY;
import static presentation.helper.OperationCode.GET_ALL;
import static presentation.validation.ValidationConstraint.NOT_EMPTY;

public class PenaltyTariffPopupController extends PenaltyTariffsMainController {
    public ComboBox<Penalty> fx_penalty;
    public TextField fx_price;
    public CustomDatePicker fx_fromDate;
    public CustomDatePicker fx_toDate;
    public GridPane gridPane;


    protected void addFieldsRestriction() {
        addPriceFieldListener(fx_price);
        fx_fromDate.disableBefore(LocalDate.now());

        fx_fromDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fx_toDate.disableBefore(fx_fromDate.getValue());
            }
            // Enable toDate after fromDate selection
            if(fx_toDate.isDisabled()) {
                fx_toDate.disableProperty().set(false);
            }
        });

        fx_toDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.isBefore(fx_fromDate.getValue())) {
                fx_toDate.setValue(null);
            }
        });
    }

    @SuppressWarnings("LawOfDemeter")
    protected boolean validateInput() {
        GridValidator gv = new GridValidator(gridPane);
        gv.addConstraint(NOT_EMPTY).validate(fx_price);
        //TODO
        //validator.validateDate(null, null, fx_fromDate, fx_fromDate_validation, fx_fromDate_label);

        return gv.isValid();
    }

    @SuppressWarnings("LawOfDemeter")
    protected final PenaltyTariff getInputData() {
        BigDecimal price = new BigDecimal(Float.parseFloat(fx_price.getText()));

        return new PenaltyTariff.Builder()
                .penalty(fx_penalty.getValue())
                .price(price)
                .fromDate(fx_fromDate.getValue())
                .toDate(fx_toDate.getValue()).build();
    }

    protected final void setPenaltyComboBox(ComboBox<Penalty> comboBox) {
        Response response = FrontController.handleRequest(GET_ALL, PENALTY, null);
        List<?> penalties = response.getList();

        ObservableList<Penalty> list = FXCollections.observableArrayList();

        for(Object object : penalties) {
            Penalty penalty = (Penalty) object;
            list.add(penalty);
        }

        comboBox.setItems(list);
        comboBox.getSelectionModel().selectFirst();

        comboBox.setCellFactory(new Callback<ListView<Penalty>, ListCell<Penalty>>() {
            @Override
            public ListCell<Penalty> call(ListView<Penalty> param) {

                return new ListCell<Penalty>(){
                    @Override
                    public void updateItem(Penalty penalty, boolean empty){
                        super.updateItem(penalty, empty);
                        if(!empty) {
                            setText(penalty.toString());
                            setGraphic(null);
                        }
                    }
                };
            }
        });
    }
}
