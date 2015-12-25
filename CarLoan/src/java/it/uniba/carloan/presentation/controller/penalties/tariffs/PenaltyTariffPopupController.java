package it.uniba.carloan.presentation.controller.penalties.tariffs;


import it.uniba.carloan.entity.Penalty;
import it.uniba.carloan.entity.PenaltyTariff;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.javafx.scene.control.CustomDatePicker;
import it.uniba.carloan.presentation.FrontController;
import it.uniba.carloan.presentation.controller.DataLoader;
import it.uniba.carloan.presentation.validation.GridValidator;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.time.LocalDate;

import static it.uniba.carloan.presentation.helper.EntityCode.PENALTY;
import static it.uniba.carloan.presentation.helper.OperationCode.GET_ALL;
import static it.uniba.carloan.presentation.validation.ValidationConstraint.NOT_EMPTY;

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

        DataLoader<Penalty> dataLoader = new DataLoader<Penalty>() {
            @Override
            protected Response dataLoadRequest() {
                return FrontController.handleRequest(GET_ALL, PENALTY, null);
            }

            @Override
            protected HBox spinnerBox() {
                return null;
            }
        };

        comboBox.setItems(dataLoader.getLoadedData());
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
