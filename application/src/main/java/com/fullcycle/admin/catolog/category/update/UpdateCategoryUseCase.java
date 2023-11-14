package com.fullcycle.admin.catolog.category.update;

import com.fullcycle.admin.catolog.UseCase;
import com.fullcycle.admin.catolog.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase
extends UseCase<UpdateCategoryCommand, Either<Notification,UpdateCategoryOutput>> {

}
