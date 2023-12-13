package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.AggregateRoot;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> implements Cloneable {

    private String name;
    private String description;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(final CategoryID anID, final String aName, final String aDescription, final boolean isActive, final Instant createdAt, final Instant updatedAt, final Instant deletedAt) {
        super(anID);
        name = aName;
        this.description = aDescription;
        this.active = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Category newCategory(final String aName, String aDescription, boolean isActive) {
        final var id = CategoryID.unique();
        Instant now = Instant.now();

        return new Category(id, aName, aDescription, isActive, now, now, isActive?null:now);
    }

    public static Category with(final CategoryID id,
                                final String aName,
                                final String aDescription,
                                final boolean isActive,
                                final Instant createdAt,
                                final Instant updatedAt,
                                final Instant deletedAt) {
        return new Category(id, aName, aDescription, isActive, createdAt, updatedAt, isActive?null:deletedAt);
    }

    public CategoryID getId() {
        return id;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public Category deactivate() {
        if(getDeletedAt()==null){
            this.deletedAt = Instant.now();
        }

        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category update(
            final String aName,
            final String aDescription,
            final boolean isActive) {
        if(isActive){
            activate();
        }else{
            deactivate();
        }

        this.name = aName;
        this.description = aDescription;
        this.updatedAt = Instant.now();
        return this;
    }

    @Override
    public Category clone() {
        try{
            return (Category) super.clone();
        }catch (CloneNotSupportedException e){
            throw new AssertionError();
        }
    }
}
