package it.uniba.carloan.presentation.controller;

import it.uniba.carloan.business.utility.SessionData;
import it.uniba.carloan.entity.Category;
import it.uniba.carloan.entity.Groupable;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.AlertHandler;
import it.uniba.carloan.presentation.FrontController;
import it.uniba.carloan.presentation.helper.EntityCode;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static it.uniba.carloan.presentation.helper.OperationCode.*;
import static it.uniba.carloan.presentation.validation.ValidationPattern.PRICE_PATTERN;


public class DefaultController {

    public HBox spinnerBox;

    public void gotoDashboard() {
        FrontController.handleRequest("Dashboard");
    }

    public void gotoContracts() {
        FrontController.handleRequest("Contracts");
    }

    public void gotoCars() {
        FrontController.handleRequest("Cars");
    }

    public void gotoUsers() {
        FrontController.handleRequest("Users");
    }

    public void gotoAgencies() {
        FrontController.handleRequest("Agencies");
    }

    public void gotoServices() {
        FrontController.handleRequest("Services");
    }

    public void gotoAccessories() {
        FrontController.handleRequest("Accessories");
    }

    public void gotoPenalties() {
        FrontController.handleRequest("Penalties");
    }

    public void gotoClients() {
        FrontController.handleRequest("Clients");
    }

    public void gotoTariffs() {
        FrontController.handleRequest("CarTariffs");
    }

    public void closeWindow() {
        Stage stage = (Stage) SessionData.currentScene.getWindow();
        stage.close();
        // reset current scene
        SessionData.currentScene = SessionData.scene;
    }


    protected void resetTextFields(TextField... args) {
        for (TextField textField : args) {
            textField.clear();
        }
    }

    protected void resetComboBoxes(ComboBox... args) {
        for (ComboBox comboBox : args) {
            comboBox.getSelectionModel().selectFirst();
        }
    }

    @SuppressWarnings("LawOfDemeter")
    protected void executeInsert(EntityCode entityCode, TransferObject object) {
        Response response = FrontController.handleRequest(ADD, entityCode, object);

        if(response.getOperationResult()) {
            closeWindow();
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Record \u00E8 aggiunto correttamente.");
            // Update Users view
            FrontController.handleRequest(SessionData.currentRootView);
        }
    }

    @SuppressWarnings("LawOfDemeter")
    protected void executeUpdate(TransferObject originalTO, TransferObject updatedTO, EntityCode entityCode) {
        if(!updatedTO.equals(originalTO)) {
            Response response = FrontController.handleRequest(UPDATE, entityCode, updatedTO);

            if(response.getOperationResult()) {
                closeWindow();
                AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Successo", "Record \u00E8 succesivamente aggiornato.");
                // Update main view
                FrontController.handleRequest(SessionData.currentRootView);
            }
        }
        else {
            closeWindow();
        }
    }

    @SuppressWarnings("LawOfDemeter")
    protected void deleteItem(TransferObject item, EntityCode entityCode) {
        if (item != null) {
            Boolean choice = AlertHandler.showAlertConfirmationDialog("Cancellazione record",
                    "Vuoi davvero cancellare record", "I dati non potranno essere ripristinati.",
                    "Cancella");

            if (choice) {
                Response response = FrontController.handleRequest(DELETE, entityCode, item);

                if (response.getOperationResult()) {
                    FrontController.handleRequest(SessionData.currentRootView);
                }
            }
        }
        else {
            AlertHandler.showAlert(Alert.AlertType.WARNING, "Attenzione", "Nessun record selezionato!");
        }
    }

    protected void addCategoryFilter(ComboBox<Category> fx_category, FilteredList<? extends Groupable> filteredList) {
        fx_category.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            Category defaultCategory = new Category.Builder("Tutti").build();

            filteredList.setPredicate( groupableObject -> {
                String categoryName = newValue.getName();
                if (newValue.equals(defaultCategory)) {
                    return groupableObject.getCategoryName().contains("");
                }
                return groupableObject.getCategoryName().equals(categoryName);
            });
        });
    }

    protected void addPriceFieldListener(final TextField... args) {
        for (TextField tf: args) {
            tf.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.equals("") && !newValue.matches(PRICE_PATTERN.getPattern())) {
                    tf.setText(oldValue);
                    tf.positionCaret(tf.getLength());
                }
            });
        }
    }


}
