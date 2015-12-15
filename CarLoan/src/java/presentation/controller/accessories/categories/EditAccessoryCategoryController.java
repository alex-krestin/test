package presentation.controller.accessories.categories;

import javafx.fxml.Initializable;
import presentation.controller.CategoryController;

import java.net.URL;
import java.util.ResourceBundle;

import static presentation.helper.EntityCode.ACCESSORY_CATEGORY;


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
