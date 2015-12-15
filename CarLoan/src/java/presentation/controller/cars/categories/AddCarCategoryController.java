package presentation.controller.cars.categories;

import presentation.controller.CategoryController;

import static presentation.helper.EntityCode.CAR_CATEGORY;


public class AddCarCategoryController extends CategoryController {

    public void handleSaveCategoryAction() {
        addCategory(CAR_CATEGORY);
    }

}
