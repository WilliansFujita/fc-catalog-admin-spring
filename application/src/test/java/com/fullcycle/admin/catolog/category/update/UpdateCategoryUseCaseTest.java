package com.fullcycle.admin.catolog.category.update;

import com.fullcycle.admin.catolog.domain.category.Category;
import com.fullcycle.admin.catolog.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
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
                aCategory.getId().getValue().toString(),
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
}
