package com.digipaper.models;

import com.digipaper.framework.generics.GenericModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User extends GenericModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter Integer id;

    @Getter @Setter String name;
    @Getter @Setter String email;
    @Getter @Setter String password;
}
