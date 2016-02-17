package com.digipaper.services;

import com.digipaper.framework.generics.GenericService;
import com.digipaper.models.Company;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;

@Stateless
@Dependent
public class CompanyService extends GenericService<Company> {
}
