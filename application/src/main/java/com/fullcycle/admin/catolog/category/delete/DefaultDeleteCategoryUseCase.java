package com.fullcycle.admin.catolog.category.delete;

import com.fullcycle.admin.catolog.domain.category.CategoryGateway;
import com.fullcycle.admin.catolog.domain.category.CategoryID;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase{

    private CategoryGateway gateway;

    public DefaultDeleteCategoryUseCase(final CategoryGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(final String anIN) {
        CategoryID id = CategoryID.from(anIN);
        this.gateway.deleteById(id);
    }
}
