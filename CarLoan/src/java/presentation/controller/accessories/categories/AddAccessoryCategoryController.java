package presentation.controller.accessories.categories;

import presentation.controller.CategoryController;

import static presentation.helper.EntityCode.ACCESSORY_CATEGORY;


public class AddAccessoryCategoryController extends CategoryController {

    public void handleSaveCategoryAction() {
        addCategory(ACCESSORY_CATEGORY);
    }

}
