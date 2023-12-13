package com.fullcycle.admin.catalogo.infrastructure.category.update;

import com.fullcycle.admin.catalogo.UseCase;
import com.fullcycle.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase
extends UseCase<UpdateCategoryCommand, Either<Notification,UpdateCategoryOutput>> {

}
