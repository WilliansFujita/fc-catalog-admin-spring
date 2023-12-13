package com.fullcycle.admin.catalogo.infrastructure.category.delete;

import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase{

    private final CategoryGateway gateway;

    public DefaultDeleteCategoryUseCase(final CategoryGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(final String anIN) {
        CategoryID id = CategoryID.from(anIN);
        this.gateway.deleteById(id);
    }
}
