package com.digipaper.models;

import com.digipaper.framework.generics.GenericModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Company extends GenericModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter Integer id;

    @Getter @Setter String name;
    @Getter @Setter Boolean isActive;
    @Getter @Setter String status;
}
