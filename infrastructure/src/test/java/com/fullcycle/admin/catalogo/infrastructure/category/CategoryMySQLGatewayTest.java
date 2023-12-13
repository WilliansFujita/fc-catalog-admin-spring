package com.fullcycle.admin.catalogo.infrastructure.category;

import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import com.fullcycle.admin.catalogo.MySQLGatewayTest;
import com.fullcycle.admin.catalogo.domain.category.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MySQLGatewayTest
public class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryMySQLGateway;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenAValidCategory_whenCallsCreate_shouldReturnANewCategoty(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A Categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(0, categoryRepository.count());

        final var actualCategory = categoryMySQLGateway.create(aCategory);

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        var actualEntity = categoryRepository.findById(actualCategory.getId().getValue()).get();

        var categoryEntity = actualEntity.toAggregate();

        Assertions.assertEquals(expectedName, categoryEntity.getName());
        Assertions.assertEquals(expectedDescription, categoryEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, categoryEntity.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), categoryEntity.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), categoryEntity.getUpdatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), categoryEntity.getDeletedAt());
        Assertions.assertNull(categoryEntity.getDeletedAt());
    }
}


