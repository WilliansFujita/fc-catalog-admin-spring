package com.fullcycle.admin.catolog.domain.category;

import com.fullcycle.admin.catolog.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category aCategory);

    void deleteById(CategoryID anId);

    Optional<Category> findByID(CategoryID anID);

    Category update(Category category);

    Pagination<Category> findAll(CategorySearchQuery aQuery);
}
