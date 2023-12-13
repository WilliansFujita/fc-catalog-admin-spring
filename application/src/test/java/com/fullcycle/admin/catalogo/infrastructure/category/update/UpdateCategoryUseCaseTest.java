package com.fullcycle.admin.catalogo.infrastructure.category.update;

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

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {


    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    private void cleanUp(){
        Mockito.reset(categoryGateway);
    }

    // 1. Teste do caminho feliz
    // 2. Teste passando uma propriedade inválida (name)
    // 3. Teste atualizando uma categoria para inativa
    // 4. Teste simulando um erro generico vendo do gateway
    // 5. Teste atualizar categoria passando ID inválido

    @Test
    public void givenAValidCommand__whenCallsUpdateCategory_shoudReturnCategoryId(){

        final var aCategory = Category.newCategory("Film",null,true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var expextedID =  aCategory.getId();


        final var aCommand = UpdateCategoryCommand.with(
                aCategory.getId().getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(categoryGateway.findByID(eq(expextedID)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).findByID(eq(expextedID));

        Mockito.verify(categoryGateway,Mockito.times(1))
                .update(Mockito.argThat(aUpdatedCategory->{
                            return Objects.equals(expectedName,aUpdatedCategory.getName())
                                    && Objects.equals(expectedDescription,aUpdatedCategory.getDescription())
                                    && Objects.equals(expectedIsActive,aUpdatedCategory.isActive())
                                    && Objects.equals(expextedID,aUpdatedCategory.getId())
                                    && Objects.nonNull(aUpdatedCategory.getCreatedAt())
                                    && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                    && Objects.isNull(aUpdatedCategory.getDeletedAt());
                        }
                ));

    }

    @Test
    public void givenAnInvalidName_whenCallsCreateCategory_thenShouldReturnADomainException(){

        final var aCategory = Category.newCategory("Film",null,true);


        final String expectedName = null;
        final var expectedDescription = "A Categoria mais assistida";
        final var expectedIsActive =  true;
        final var expectedExceptionMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var id = aCategory.getId();
        final var aCommand = UpdateCategoryCommand.with(id.getValue(),expectedName,expectedDescription,expectedIsActive);

        when(categoryGateway.findByID(eq(id)))
                .thenReturn(Optional.of(aCategory.clone()));

//        when(categoryGateway.update(any()))
//                .thenAnswer(returnsFirstArg());

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedExceptionMessage, notification.firstError().message());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Mockito.verify(categoryGateway,times(0)).update(any());
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsUpdateCategory_shoudReturnCategoryId(){

        final var aCategory = Category.newCategory("Film",null,true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var expextedID =  aCategory.getId();


        final var aCommand = UpdateCategoryCommand.with(
                aCategory.getId().getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(categoryGateway.findByID(eq(expextedID)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).findByID(eq(expextedID));

        Mockito.verify(categoryGateway,Mockito.times(1))
                .update(Mockito.argThat(aUpdatedCategory->{
                            return Objects.equals(expectedName,aUpdatedCategory.getName())
                                    && Objects.equals(expectedDescription,aUpdatedCategory.getDescription())
                                    && Objects.equals(expectedIsActive,aUpdatedCategory.isActive())
                                    && Objects.equals(expextedID,aUpdatedCategory.getId())
                                    && Objects.nonNull(aUpdatedCategory.getCreatedAt())
                                    && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                    && Objects.nonNull(aUpdatedCategory.getDeletedAt());
                        }
                ));

    }

    @Test
    public void givenAValidCommand_WhenGatewayThrowsAGenericError_thenShouldThrowAnError(){

        final var aCategory = Category.newCategory("Film",null,true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A Categoria mais assistida";
        final var expectedIsActive =  true;
        final var expectedErrorMessage = "gateway error";
        final var expectedErrorCount = 1;

        final var expextedID =  aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(expextedID.getValue(), expectedName,expectedDescription,expectedIsActive);

        when(categoryGateway.findByID(eq(expextedID)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(Mockito.any()))
                .thenThrow(new IllegalStateException("gateway error"));


        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Mockito.verify(categoryGateway,Mockito.times(1))
                .update(Mockito.argThat(actualCategory->{
                            return Objects.equals(expectedName,actualCategory.getName())
                                    && Objects.equals(expectedDescription,actualCategory.getDescription())
                                    && Objects.equals(expectedIsActive,actualCategory.isActive())
                                    && Objects.nonNull(actualCategory.getId())
                                    && Objects.nonNull(actualCategory.getCreatedAt())
                                    && Objects.nonNull(actualCategory.getUpdatedAt())
                                    && Objects.isNull(actualCategory.getDeletedAt());
                        }
                ));

    }

    @Test
    public void givenACommandWithAnInvalidID_whenCallsUpdateCategory_shoudReturnNotFoundException(){

        final var aCategory = Category.newCategory("Film",null,true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedErrorMessage="Category with ID 123 was not-found";
        final var expectedCountError=1;

        final var expextedID =  "123";


        final var aCommand = UpdateCategoryCommand.with(
                expextedID,
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(categoryGateway.findByID(eq(CategoryID.from(expextedID))))
                .thenReturn(Optional.empty());

        final var actualException =
               Assertions.assertThrows(DomainException.class, ()->useCase.execute(aCommand));


        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedCountError, actualException.getErrors().size());

        Mockito.verify(categoryGateway, times(1)).findByID(eq(CategoryID.from(expextedID)));

        Mockito.verify(categoryGateway,Mockito.times(0))
                .update(any());

    }
}
