package com.fullcycle.admin.catalogo.domain.validation.handler;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.Error;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private List<Error> erros;

    private Notification(List<Error> erros) {
        this.erros = erros;
    }

    public static Notification create(){
        return new Notification(new ArrayList<>());
    }

    public static Notification create(Error anError){
        return new Notification(List.of(anError));
    }

    public static Notification create(final Throwable t){
        return new Notification(List.of(new Error(t.getMessage())));
    }

    @Override
    public ValidationHandler append(Error anError) {
        this.erros.add(anError);
        return this;
    }

    @Override
    public ValidationHandler append(final ValidationHandler handler) {
        this.erros.addAll(handler.getErrors());
        return this;
    }

    @Override
    public ValidationHandler validate(final Validation aValidation) {
        try{
            aValidation.validate();
        }catch (DomainException ex){
            erros.addAll(ex.getErrors());
        }catch (Throwable t){
            erros.add(new Error(t.getMessage()));
        }
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.erros;
    }

    @Override
    public boolean hasErrors() {
        return ValidationHandler.super.hasErrors();
    }
}
