package it.uniba.carloan.presentation.controller.accessories.categories;

import it.uniba.carloan.presentation.controller.CategoryController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import static it.uniba.carloan.presentation.helper.EntityCode.ACCESSORY_CATEGORY;


public class EditAccessoryCategoryController extends CategoryController implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fx_title.setText(currentCategory.getName());
        fx_description.setText(currentCategory.getDescription());
    }

    public void handleEditCategoryAction() {
        editCategory(currentCategory, ACCESSORY_CATEGORY);
    }
}
