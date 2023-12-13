package com.fullcycle.admin.catalogo.domain.exceptions;

import com.fullcycle.admin.catalogo.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStackTraceException{

    private final List<Error> errors;

    private DomainException(final String aMessage, final List<Error> anErrors){
        super(aMessage);
        this.errors = anErrors;
    }

    public static DomainException with(List<Error> anErros){
        return new DomainException("",anErros);
    }

    public static DomainException with(Error anErro){
        return new DomainException(anErro.message(),List.of(anErro));
    }

    public List<Error> getErrors() {
        return errors;
    }
}
