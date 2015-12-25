package it.uniba.carloan.presentation.controller.cars.categories;

import it.uniba.carloan.presentation.controller.CategoryController;

import static it.uniba.carloan.presentation.helper.EntityCode.CAR_CATEGORY;


public class AddCarCategoryController extends CategoryController {

    public void handleSaveCategoryAction() {
        addCategory(CAR_CATEGORY);
    }

}
