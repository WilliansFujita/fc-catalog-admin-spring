package com.fullcycle.admin.catolog.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler handler);

    ValidationHandler validate(Validation aValidation);

    List<Error> getErrors();
    default boolean hasErrors(){
        return getErrors() != null && !getErrors().isEmpty();
    }
    interface Validation{
        void validate();
    }

    default Error firstError(){
        if(getErrors() != null){
            return getErrors(). get(0);
        }else{
            return null;
        }
    }


}
