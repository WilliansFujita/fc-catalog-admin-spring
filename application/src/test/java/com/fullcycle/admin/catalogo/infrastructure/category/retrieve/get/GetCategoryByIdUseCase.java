package com.fullcycle.admin.catalogo.infrastructure.category.retrieve.get;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByIdUseCase {

    @InjectMocks
    private DefaultGetCategoryByIdUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    private void cleanUp(){
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidID_WhenCallGetCategory_ShouldReturnCategory(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var expectedId = aCategory.getId();

        Mockito.when(categoryGateway.findByID(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        final var actualCategory = useCase.execute(expectedId.getValue());

        Assertions.assertEquals(expectedName, actualCategory.name());
        Assertions.assertEquals(expectedId, actualCategory.id());
        Assertions.assertEquals(expectedDescription, actualCategory.description());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.createdAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategory.updatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.deletedAt());
        Assertions.assertEquals(CategoryOutput.from(aCategory), actualCategory);
    }
    @Test
    public void givenAnInValidID_WhenCallGetCategory_ShouldReturnNotFond(){
        final var expectedId = CategoryID.from( "123");
        String expectedErrorMessage = "Category with ID 123 was not found";

        Mockito.when(categoryGateway.findByID(Mockito.eq(expectedId)))
                .thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(DomainException.class,
                ()->useCase.execute(expectedId.getValue()));

        Assertions.assertEquals(actualException.getMessage(), expectedErrorMessage);
    }
    @Test
    public void givenAValidID_WhenGatewayThrowsExcpetion_ShouldReturnException(){
        final var expectedId = CategoryID.from( "123");
        String expectedErrorMessage = "Gateway Error";

        Mockito.when(categoryGateway.findByID(Mockito.eq(expectedId)))
                .thenThrow(new IllegalStateException("Gateway Error"));

        final var actualException = Assertions.assertThrows(IllegalStateException.class,
                ()->useCase.execute(expectedId.getValue()));

        Assertions.assertEquals(actualException.getMessage(), expectedErrorMessage);
    }
}
