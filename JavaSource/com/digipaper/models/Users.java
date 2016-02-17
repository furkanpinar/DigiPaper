package com.digipaper.models;

import com.digipaper.framework.generics.GenericModel;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;

@Entity
public class Users extends GenericModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter Integer id;

    @Getter @Setter String name;
    @Getter @Setter String email;
    @Getter @Setter String password;

    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
