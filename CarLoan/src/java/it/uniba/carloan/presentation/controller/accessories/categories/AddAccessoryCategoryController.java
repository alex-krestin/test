package it.uniba.carloan.presentation.controller.accessories.categories;

import it.uniba.carloan.presentation.controller.CategoryController;

import static it.uniba.carloan.presentation.helper.EntityCode.ACCESSORY_CATEGORY;


public class AddAccessoryCategoryController extends CategoryController {

    public void handleSaveCategoryAction() {
        addCategory(ACCESSORY_CATEGORY);
    }

}
