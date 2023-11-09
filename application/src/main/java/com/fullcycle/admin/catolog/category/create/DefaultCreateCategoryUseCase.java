package com.fullcycle.admin.catolog.category.create;

import com.fullcycle.admin.catolog.domain.category.Category;
import com.fullcycle.admin.catolog.domain.category.CategoryGateway;
import com.fullcycle.admin.catolog.domain.validation.handler.Notification;
import com.fullcycle.admin.catolog.domain.validation.handler.ThrowsValidationHandler;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Left;

public class DefaultCreateCategoryUseCase  extends CreateCategoryUseCase{

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(final CreateCategoryCommand aCommand) {
        final var aCategory = Category.newCategory(aCommand.name(), aCommand.description(), aCommand.isActive());

        Notification notification = Notification.create();
        aCategory.validate(notification);

        return notification.hasErrors()? Left(notification): create(aCategory);
    }

    private Either<Notification, CreateCategoryOutput> create(final Category aCategory) {
            return API.Try(()->this.categoryGateway.create(aCategory))
                    .toEither()
                    .bimap(Notification::create, CreateCategoryOutput::from);
    }
}
