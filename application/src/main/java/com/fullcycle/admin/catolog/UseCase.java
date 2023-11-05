package com.fullcycle.admin.catolog;


import com.fullcycle.admin.catolog.domain.category.Category;

public abstract class UseCase<IN,OUT> {
    public abstract OUT execute(IN anInput);
}