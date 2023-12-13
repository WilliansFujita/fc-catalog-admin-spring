package com.fullcycle.admin.catalogo.infrastructure.category.retrieve.get;

import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.Error;

import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private CategoryGateway gateway;

    public DefaultGetCategoryByIdUseCase(final CategoryGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public CategoryOutput execute(String anInput) {
        final var anCategoryID = CategoryID.from(anInput);
        return this.gateway.findByID(anCategoryID)
                .map(aCategory-> CategoryOutput.from(aCategory))
                .orElseThrow(notFound(anCategoryID));


    }

    private Supplier<DomainException> notFound(final CategoryID anID) {
        return ()->DomainException.with(
                new Error("Category with ID %s was not found".formatted(anID.getValue()))
        );
    }
}
