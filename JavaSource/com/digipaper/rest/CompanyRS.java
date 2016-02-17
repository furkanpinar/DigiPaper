package com.digipaper.rest;

import com.digipaper.framework.generics.GenericRS;
import com.digipaper.framework.generics.GenericService;
import com.digipaper.models.Company;
import com.digipaper.services.CompanyService;

import javax.inject.Inject;
import javax.ws.rs.Path;

@Path("/Company")
public class CompanyRS extends GenericRS<Company> {

    @Inject
    CompanyService companyService;

    @Override
    protected GenericService<Company> genericService() {
        return companyService;
    }
}
