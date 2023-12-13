package com.fullcycle.admin.catalogo.infrastructure.category.create;

import com.fullcycle.admin.catalogo.UseCase;
import com.fullcycle.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification,CreateCategoryOutput>> {
}
