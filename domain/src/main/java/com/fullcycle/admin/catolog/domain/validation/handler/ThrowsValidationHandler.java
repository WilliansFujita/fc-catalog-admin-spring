package com.fullcycle.admin.catolog.domain.validation.handler;

import com.fullcycle.admin.catolog.domain.exceptions.DomainException;
import com.fullcycle.admin.catolog.domain.validation.Error;
import com.fullcycle.admin.catolog.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(Error anError)
    {
        throw DomainException.with(anError);
    }

    @Override
    public ValidationHandler append(ValidationHandler anHhandler) {
        throw DomainException.with(anHhandler.getErrors());
    }

    @Override
    public ValidationHandler validate(Validation aValidation) {
        try {
            aValidation.validate();
        }catch (Exception ex){
            throw DomainException.with(List.of(new Error(ex.getMessage())));
        }

        return this;
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}
