package it.uniba.carloan.presentation.controller.cars.categories;

import it.uniba.carloan.presentation.controller.CategoryController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.CAR_CATEGORY;


public class EditCarCategoryController extends CategoryController implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_title.setText(currentCategory.getName());
        fx_description.setText(currentCategory.getDescription());
    }

    public void handleEditCategoryAction() {
        editCategory(currentCategory, CAR_CATEGORY);
    }


}
