package com.fullcycle.admin.catolog.category.create;

import com.fullcycle.admin.catolog.domain.category.CategoryGateway;
import com.fullcycle.admin.catolog.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {
    //Teste do caminho feliz
    //Teste passando uma propriedade inválida
    //Teste criando uma categoria inativa
    //Teste simulando um erro genérico vindo do gateway

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateWay;

    @Test
    public void givenAValidCommand_WhenCallCreateCategory_thenShouldReturnACategoryID(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A Categoria mais assistida";
        final var expectedIsActive =  true;

        final var aCommand = CreateCategoryCommand.with(expectedName,expectedDescription,expectedIsActive);

        when(categoryGateWay.create(Mockito.any()))
                .thenAnswer(returnsFirstArg());


        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.get().id());

        Mockito.verify(categoryGateWay,Mockito.times(1))
                .create(Mockito.argThat(actualCategory->{
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
    public void givenAnInvalidName_whenCallsCreateCategory_thenShouldReturnADomainException(){
        final String expectedName = null;
        final var expectedDescription = "A Categoria mais assistida";
        final var expectedIsActive =  true;
        final var expectedExceptionMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCategoryCommand.with(expectedName,expectedDescription,expectedIsActive);

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedExceptionMessage, notification.firstError().message());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Mockito.verify(categoryGateWay,times(0)).create(any());
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_WhenCallCreateCategory_thenShouldReturnACategoryID(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A Categoria mais assistida";
        final var expectedIsActive =  false;

        final var aCommand = CreateCategoryCommand.with(expectedName,expectedDescription,expectedIsActive);

        when(categoryGateWay.create(Mockito.any()))
                .thenAnswer(returnsFirstArg());


        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.get().id());

        Mockito.verify(categoryGateWay,Mockito.times(1))
                .create(Mockito.argThat(actualCategory->{
                            return Objects.equals(expectedName,actualCategory.getName())
                                    && Objects.equals(expectedDescription,actualCategory.getDescription())
                                    && Objects.equals(expectedIsActive,actualCategory.isActive())
                                    && Objects.nonNull(actualCategory.getId())
                                    && Objects.nonNull(actualCategory.getCreatedAt())
                                    && Objects.nonNull(actualCategory.getUpdatedAt())
                                    && Objects.nonNull(actualCategory.getDeletedAt());
                        }
                ));

    }

    @Test
    public void givenAValidCommand_WhenCreateCategoryThrownsAGenericError_thenShouldThrowAnError(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A Categoria mais assistida";
        final var expectedIsActive =  true;
        final var expectedErrorMessage = "gateway error";
        final var expectedErrorCount = 1;


        final var aCommand = CreateCategoryCommand.with(expectedName,expectedDescription,expectedIsActive);

        when(categoryGateWay.create(Mockito.any()))
                .thenThrow(new IllegalStateException("gateway error"));


        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Mockito.verify(categoryGateWay,Mockito.times(1))
                .create(Mockito.argThat(actualCategory->{
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
}
