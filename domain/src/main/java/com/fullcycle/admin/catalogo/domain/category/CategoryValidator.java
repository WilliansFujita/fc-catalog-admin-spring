package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.validation.Error;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;
import com.fullcycle.admin.catalogo.domain.validation.Validator;

public class CategoryValidator extends Validator {
    private final Category category;

    protected CategoryValidator(final Category aCategory, final ValidationHandler handler) {
        super(handler);
        this.category = aCategory;
    }

    @Override
    public void validate() {
        checkNameConstraint();
    }

    private void checkNameConstraint() {
        if(this.category.getName()==null){
            this.validationHandler().append(new Error("'name' should not be null"));
        }

        if(this.category.getName()!=null && this.category.getName().isBlank()){
            this.validationHandler().append(new Error("'name' should not be empty"));
        }

        if(this.category.getName()!=null){
            int length = this.category.getName().trim().length();
            if(length >255 || length<3){
                this.validationHandler().append(new Error("'name' must be between 3 an 255 characters"));
            }
        }



    }
}
