package it.uniba.carloan.presentation.controller.penalties.categories;

import it.uniba.carloan.presentation.controller.CategoryController;

import static it.uniba.carloan.presentation.helper.EntityCode.PENALTY_CATEGORY;


public class AddPenaltyCategoryController extends CategoryController {

    public void handleSaveCategoryAction() {
        addCategory(PENALTY_CATEGORY);
    }
}
