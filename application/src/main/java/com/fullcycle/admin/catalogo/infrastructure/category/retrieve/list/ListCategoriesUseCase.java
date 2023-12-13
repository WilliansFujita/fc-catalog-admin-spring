package com.fullcycle.admin.catalogo.infrastructure.category.retrieve.list;

import com.fullcycle.admin.catalogo.UseCase;
import com.fullcycle.admin.catalogo.domain.category.CategorySearchQuery;
import com.fullcycle.admin.catalogo.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
