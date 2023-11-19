package com.fullcycle.admin.catolog.category.retrieve.list;

import com.fullcycle.admin.catolog.UseCase;
import com.fullcycle.admin.catolog.domain.category.CategorySearchQuery;
import com.fullcycle.admin.catolog.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
