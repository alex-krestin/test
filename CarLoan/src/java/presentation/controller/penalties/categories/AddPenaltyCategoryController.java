package presentation.controller.penalties.categories;

import presentation.controller.CategoryController;

import static presentation.helper.EntityCode.PENALTY_CATEGORY;


public class AddPenaltyCategoryController extends CategoryController {

    public void handleSaveCategoryAction() {
        addCategory(PENALTY_CATEGORY);
    }
}
