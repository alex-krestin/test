package it.uniba.carloan.presentation.controller.contracts;


import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class AddContractSummaryTabController extends AddContractMainController implements Initializable{
    public TextField fx_base_price;
    public TextField fx_services_price;
    public TextField fx_accessories_price;
    public TextField fx_discount;
    public TextField fx_taxable;
    public TextField fx_vat;
    public TextField fx_vat_total;
    public TextField fx_total;
    public TextField fx_franchise;
    public TextField fx_booking_deposit;
    public ToggleGroup booking_paid;
    public RadioButton fx_bp_yes;
    public RadioButton fx_bp_no;
    public TextField fx_franchise_deposit;
    public ToggleGroup franchise_paid;
    public RadioButton fx_fp_yes;
    public RadioButton fx_fp_no;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void update() {
        fx_base_price.setText(mainContract.getBasePrice().toString());
        fx_services_price.setText(mainContract.getServicesPrice().toString());
        fx_accessories_price.setText(mainContract.getAccessoriesPrice().toString());
        fx_discount.setText(mainContract.getDiscount().toString());
        fx_taxable.setText(mainContract.getTaxableAmount().toString());
        fx_vat.setText(mainContract.getVAT().toString());
        fx_vat_total.setText(mainContract.getVatAmount().toString());
        fx_total.setText(mainContract.getTotalAmount().toString());
        fx_franchise.setText(mainContract.getFranchise().toString());
    }
}
