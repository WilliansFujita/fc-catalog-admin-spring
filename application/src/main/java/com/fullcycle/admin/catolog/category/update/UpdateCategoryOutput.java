package com.fullcycle.admin.catolog.category.update;

import com.fullcycle.admin.catolog.domain.category.Category;
import com.fullcycle.admin.catolog.domain.category.CategoryID;

public record UpdateCategoryOutput(
        CategoryID id
) {

    public static UpdateCategoryOutput from(final Category aCategory){
        return new UpdateCategoryOutput(aCategory.getId());
    }
}
