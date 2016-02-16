package com.digipaper.models;

import com.digipaper.framework.generics.GenericModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Category extends GenericModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter Integer id;

    @Getter @Setter String name;
    @Getter @Setter Boolean isActive;
    @Getter @Setter String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category parentCategory;

    @Override
    public String toString() {
        return "category [id=" + id + ", name=" + name + "]";
    }

    public Category getCategory() {
        return parentCategory;
    }

    public void setCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }
}
