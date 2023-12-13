package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class CategoryID extends Identifier {
    private final String values;

    public CategoryID(String values) {
        Objects.requireNonNull(values);
        this.values = values;
    }

    public static CategoryID unique(){
        return CategoryID.from(UUID.randomUUID());
    }

    public static CategoryID from(final String anID){
        return new CategoryID(anID);
    }

    public static CategoryID from(final UUID anId){
        return new CategoryID(anId.toString().toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryID that = (CategoryID) o;
        return Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    public String getValue() {
        return this.values.toString();
    }
}
