package com.digipaper.models;

import com.digipaper.framework.generics.GenericModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Category extends GenericModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter Integer id;

    @Getter @Setter String name;
    @Getter @Setter Date createDate;
    @Getter @Setter Boolean isActive;
    @Getter @Setter String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category parentCategory;

    public Category getCategory() {
        return parentCategory;
    }

    public void setCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }
}
