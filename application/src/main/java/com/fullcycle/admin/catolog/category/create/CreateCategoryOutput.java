package com.fullcycle.admin.catolog.category.create;

import com.fullcycle.admin.catolog.domain.category.Category;
import com.fullcycle.admin.catolog.domain.category.CategoryID;

public record CreateCategoryOutput(
        CategoryID id
) {
    public static CreateCategoryOutput from(final Category category){
        return new CreateCategoryOutput(category.getId());
    }
}
