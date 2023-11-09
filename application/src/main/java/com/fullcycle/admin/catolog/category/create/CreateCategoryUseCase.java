package com.fullcycle.admin.catolog.category.create;

import com.fullcycle.admin.catolog.UseCase;
import com.fullcycle.admin.catolog.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification,CreateCategoryOutput>> {
}
