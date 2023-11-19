package com.fullcycle.admin.catolog.category.retrieve.list;

import com.fullcycle.admin.catolog.domain.category.CategoryGateway;
import com.fullcycle.admin.catolog.domain.category.CategorySearchQuery;
import com.fullcycle.admin.catolog.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase{

    private final CategoryGateway categoryGateway;

    public DefaultListCategoriesUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(CategorySearchQuery aQuery) {
        return this.categoryGateway.findAll(aQuery)
                .map(CategoryListOutput::from);
    }
}
