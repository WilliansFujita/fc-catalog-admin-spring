package com.fullcycle.admin.catolog.category.delete;

import com.fullcycle.admin.catolog.domain.category.Category;
import com.fullcycle.admin.catolog.domain.category.CategoryGateway;
import com.fullcycle.admin.catolog.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp(){
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidId_WhenCallsDeleteCategory_shouldBeOK(){
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida.", true);
        final var expectedID = aCategory.getId();

        Mockito.doNothing()
                .when(categoryGateway)
                .deleteById(eq(expectedID));


        Assertions.assertDoesNotThrow(()-> useCase.execute(expectedID.getValue()));

        Mockito.verify(categoryGateway, Mockito.times(1)).deleteById(eq(expectedID));
    }

    @Test
    public void givenAnInvalidId_WhenCallsDeleteCategory_shouldBeOK(){
        final var expectedID = CategoryID.from("123");

        Mockito.doNothing()
                .when(categoryGateway)
                .deleteById(eq(expectedID));


        Assertions.assertDoesNotThrow(()-> useCase.execute(expectedID.getValue()));

        Mockito.verify(categoryGateway, Mockito.times(1)).deleteById(eq(expectedID));
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException(){
        final var expectedID = CategoryID.from("123");

        Mockito.doThrow(new IllegalStateException("Gateway error"))
                .when(categoryGateway)
                .deleteById(eq(expectedID));


        Assertions.assertThrows(IllegalStateException.class,()-> useCase.execute(expectedID.getValue()));

        Mockito.verify(categoryGateway, Mockito.times(1)).deleteById(eq(expectedID));
    }
}
